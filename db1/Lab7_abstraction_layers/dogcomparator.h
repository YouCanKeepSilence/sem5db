#ifndef DOGCOMPARATOR_H
#define DOGCOMPARATOR_H
#include "abstractcomparator.h"
#include "dog.h"

class NameEqualComparator : public AbstractComparator
{
public:
    NameEqualComparator() {}

    virtual bool operator()(const Dog& dog1, const Dog& dog2)
    {
        return dog1.getName() == dog2.getName();
    }
};

class AgeEqualComparator : public AbstractComparator
{
public:
    AgeEqualComparator() {}

    virtual bool operator()(const Dog& dog1, const Dog& dog2)
    {
        return dog1.getAge() == dog2.getAge();
    }
};

#endif // DOGCOMPARATOR_H
