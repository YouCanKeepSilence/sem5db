#ifndef MIDDLEWARE_H
#define MIDDLEWARE_H
#include "dog.h"
#include "abstractcomparator.h"
#include "dogcomparator.h"
#include "middleware.h"
#include <QFile>
#include <QDebug>

enum SEARCH_TYPE {
    Name = 0,
    Age = 1
};

struct DogC {
    Dog* items;
    int length;
};

template <typename T>
class Middleware
{
public:
    Middleware(QString file = "dogs.db")
    {
        this->equal = Q_NULLPTR;
        this->fileName = file;
    }

    void setSearchType(SEARCH_TYPE type)
    {
        switch (type) {
        case Name:
            this->equal = new NameEqualComparator;
            break;
        case Age:
            this->equal = new AgeEqualComparator;
        default:
            this->equal = Q_NULLPTR;
            break;
        }
    }

    void setFileName(QString fileName) { this->fileName = fileName; }

    QVector<T> findEquals(T& whatToFind)
    {
        if(this->equal == Q_NULLPTR)
        {
            qCritical() << "Error with null comparator";
        }

        if(this->fileName.isEmpty())
        {
            qCritical() << "DB name is empty";
        }

        QFile file(this->fileName);
        file.open(QFile::ReadOnly);

        if(!file.isOpen())
        {
            qCritical() << "Error with open file";
        }
        QDataStream in(&file);
        QVector<T> results;
        while(true)
        {
            T buf;
            in >> buf;
            if((*equal)(buf, whatToFind))
            {
                results.push_back(buf);
            }
            if(in.atEnd())
            {
                break;
                file.close();
            }
        }
        return results;
    }

    void addEntity(T& entity)
    {
        QFile file(this->fileName);
        file.open(QFile::WriteOnly | QFile::Append);
        if(!file.isOpen())
        {
            qCritical() << "Error with open file";
        }

        QDataStream out(&file);
        out << entity;
        file.close();
    }

    void DropTheBase()
    {
        QFile file(this->fileName);
        file.remove();
    }

    DogC* getAll() {
        if(this->fileName.isEmpty())
        {
            qCritical() << "DB name is empty";
        }
        QFile file(this->fileName);
        file.open(QFile::ReadOnly);

        if(!file.isOpen())
        {
            qCritical() << "Error with open file";
        }
        QDataStream in(&file);
        QVector<T> results;
        while(true)
        {
            T buf;
            in >> buf;
            results.push_back(buf);
            if(in.atEnd())
            {
                break;
                file.close();
            }
        }
        DogC* answer = new DogC();
//        for (int i = 0; i < results.length(); i++) {
//            results.data()[i].print();
//        }
        std::cout<< "-----------------1\n";
        answer->items = results.data();
        answer->items = new Dog[results.length()];
        for (int i = 0; i < results.length(); i++) {
            answer->items[i] = results.at(i);
            answer->items[i].print();
        }
        answer->length = results.length();
        return answer;
    }

private:
    AbstractComparator* equal;
    QString fileName;
};

#endif // MIDDLEWARE_H
