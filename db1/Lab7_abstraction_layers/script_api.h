#ifndef SCRIPT_API_H
#define SCRIPT_API_H
#include "middleware.h"

extern "C" {
    Dog* getAllDogs()
    {
        Middleware<Dog> mv;
        DogC* answer = mv.getAll();
        std::cout<<"----------------3\n";
        for (int i =0 ; i < answer->length; i++) {
            answer->items[i].print();
        }
        return answer->items;
    }
}



#endif // SCRIPT_API_H
