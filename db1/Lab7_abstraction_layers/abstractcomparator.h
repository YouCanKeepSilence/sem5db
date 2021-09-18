#ifndef ABSTRACTCOMPARATOR_H
#define ABSTRACTCOMPARATOR_H
#include "dog.h"

class AbstractComparator
{
public:
    AbstractComparator() {}

    ~AbstractComparator() {}

    virtual bool operator()(const Dog& dog1, const Dog& dog2) = 0;
};

#endif // ABSTRACTCOMPARATOR_H
