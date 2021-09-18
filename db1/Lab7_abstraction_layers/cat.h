#ifndef CAT_H
#define CAT_H
#include <string>
#include <QVector>
#include <QVariant>
// Класс хранящий значение свойств кота
class Value
{
public:
    Value(QString name, QVariant value);
private:
    QString m_name;
    // Стоит ли добавить тип? и каким образом ??
    QVariant m_value;
};

class Cat
{
public:
    Cat();
    Cat(QVector<Value> &properties);
    QVariant getValueWithName(QString name);
    void setValueWithName(QString name, QVariant newValue);
    void addValueWithName(QString name, QVariant value);
private:
    Value& findValueWithName(QString name);
    QVector<Value> m_properties;
};

#endif // CAT_H
