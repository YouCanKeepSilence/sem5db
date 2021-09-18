extern "C" {
#include "postgres.h"
#include "fmgr.h"
#include <funcapi.h>
#include <utils/array.h>
#include <catalog/pg_type.h>
}
#include <stdlib.h>
#include <string.h>
#include "Dog.h"
#include "dataWorker.h"

extern "C" {
#ifdef PG_MODULE_MAGIC
PG_MODULE_MAGIC;
#endif
}

// Функция потягушек
extern "C"
{
    std::vector<Dog> getAllDog()
    {
        DataWorker<Dog> dw;
        return dw.getAll();
    }
}

extern "C"
{
    PG_FUNCTION_INFO_V1(get_all_dogs);
}

extern "C"
{

	Datum
    get_all_dogs(PG_FUNCTION_ARGS)
	{
        FuncCallContext     *funcctx;
            int                  call_cntr;
            int                  max_calls;
            TupleDesc            tupdesc;
            AttInMetadata       *attinmeta;
            // Тянем всех собак
            vector<Dog> dogs = getAllDog();
            // Пригодится в первом ифе
            unsigned int size = dogs.size();
            if (SRF_IS_FIRSTCALL())
            {
                MemoryContext   oldcontext;
                funcctx = SRF_FIRSTCALL_INIT();
                oldcontext = MemoryContextSwitchTo(funcctx->multi_call_memory_ctx);
                // ВОТ ТУТ РАЗМЕР
                funcctx->max_calls = size;
                if (get_call_result_type(fcinfo, NULL, &tupdesc) != TYPEFUNC_COMPOSITE)
                    ereport(ERROR,
                            (errcode(ERRCODE_FEATURE_NOT_SUPPORTED),
                             errmsg("function returning record called in context "
                                    "that cannot accept type record")));
                attinmeta = TupleDescGetAttInMetadata(tupdesc);
                funcctx->attinmeta = attinmeta;

                MemoryContextSwitchTo(oldcontext);
            }

            /* stuff done on every call of the function */
            funcctx = SRF_PERCALL_SETUP();

            call_cntr = funcctx->call_cntr;
            max_calls = funcctx->max_calls;
            attinmeta = funcctx->attinmeta;

            if (call_cntr < max_calls)    /* do when there is more left to send */
            {
                char       **values;
                HeapTuple    tuple;
                Datum        result;

                /*
                 * Prepare a values array for building the returned tuple.
                 * This should be an array of C strings which will
                 * be processed later by the type input functions.
                 */
                values = (char **) palloc(2 * sizeof(char *));
                values[0] = (char *) palloc(64 * sizeof(char));
                values[1] = (char *) palloc(16 * sizeof(char));
                // СНАЧАЛА СТРОКИ ИНАЧЕ ПОЙДЕШЬ В ПЕКЛО
                snprintf(values[0], 64, "%s", (char*)dogs[call_cntr].getName().c_str());
                snprintf(values[1], 16, "%d", dogs[call_cntr].getAge());

                /* build a tuple */
                tuple = BuildTupleFromCStrings(attinmeta, values);

                /* make the tuple into a datum */
                result = HeapTupleGetDatum(tuple);

                SRF_RETURN_NEXT(funcctx, result);
            }
            else    /* do when there is no more left */
            {
                SRF_RETURN_DONE(funcctx);
            }
	}
}

