//
// Created by Silence on 10.02.2018.
//

#ifndef LAB1_DATAWORKER_H
#define LAB1_DATAWORKER_H

#include "Dog.h"
#include "fstream"
#include <vector>
template <typename T>
class DataWorker {
private:
    std::string fileName;

public:

    DataWorker(string file = "/Users/silence/Workbench/Actual_Study/Production/Bondin stuff/Silence/db2/Lab1/dogs.db")
    {
        this->fileName = file;
    }

    std::vector<T> getAll()
    {
        if(this->fileName.empty())
        {
            std::cerr << "DB name is empty";
        }
        ifstream file;

        file.open(this->fileName);
        if (!file.is_open())
        {
            std::cerr << "FILE WONT OPEN!";
        }
        std::vector<T> results;
        while(true)
        {
            T buf;
            file >> buf;
            if(file.eof())
            {
                file.close();
                break;
            }
            results.push_back(buf);

        }
        return results;
    }
};


#endif //LAB1_DATAWORKER_H
