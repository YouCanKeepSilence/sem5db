#include <iostream>
#include "people.h"
#include "odbcEnv.h"
#include "contact.h"
#include <string>

using namespace std;

void printVector(vector<Contact> v)
{
    for(int i = 0; i < v.size(); ++i)
    {
        v[i].print();
    }
}

void printVector(vector<PeopleEntity> v)
{
    for(int i = 0; i < v.size(); ++i)
    {
        v[i].print();
    }
}

int main()
{
    OdbcEnvirioment * env = new OdbcEnvirioment();
    if(env->connect())
    {
        cerr << "Error with connection" << endl;
        return 1;
    }
    PeopleService * service = new PeopleService(env);
    ContactService * cservice = new ContactService(env);
    cout << "Succesful connect" << endl;
    while(true)
    {
        int menu = 0;
        vector<PeopleEntity> v = service->getAll();
        cout << "1) Choose user by id \n2) Change user by id \n3) Delete user by id \n4) Find user \n5) Show all users \n6) Add user \n7) Exit" << endl;
        cin >> menu;
        cin.ignore();
        int man_id = 0;
        switch(menu)
        {
        case 1:
        {
            printVector(v);
            cout << "Enter id of user: ";
            cin >> man_id;
            cout << "1) Show all phones \n2) Show all emails \n3) Show all contacts" << endl;
            int subOption = 0;
            cin >> subOption;
            vector<Contact> res;
            try
            {
                switch (subOption)
                {
                case 1:
                {
                    res = cservice->getContactsByUserId(man_id , true);
                    break;
                }
                case 2:
                {
                    res = cservice->getContactsByUserId(man_id , false);
                    break;
                }
                case 3:
                {
                    res = cservice->getAllContactsByUserId(man_id);
                    break;
                }
                default:
                {
                    cout << "Uncorrect menu option"<<endl;
                    break;
                }
                }
            }
            catch (const char * err)
            {
                cerr << err << endl;
                return 1;
            }
            printVector(res);
            break;
        }
        case 2:
        {
            printVector(v);
            cout << "Enter id of user: ";
            cin >> man_id;
            cout << "1) Add contact \n2) Delete one contact \n3) Update contacts\n4) Update user info \n5) Delete all contact for user"<< endl;
            int subOption = 0;
            int phone = 0;
            int contactId = 0;
            bool flag = false;
            cin >> subOption;
            if(subOption > 0 && subOption < 4 )
            {
                cout << "is it phone ? (1 - yes; 2 - no): ";
                cin >> phone;
                if (phone == 1)
                {
                    flag = true;
                }
                else
                {
                    flag = false;
                }
            }
            vector<Contact> res;
            try
            {
                switch (subOption)
                {
                case 1:
                {
                    cout << "Enter contact info: ";
                    string str;
                    cin.ignore();
                    getline(cin, str);
                    cservice->addContact(man_id, str, flag);
                    break;
                }
                case 2:
                {
                    res = cservice->getContactsByUserId(man_id, flag);
                    printVector(res);
                    cout << "Enter id of contact: ";
                    cin >> contactId;
                    cservice->removeContact(contactId, flag);
                    break;
                }
                case 3:
                {
                    res = cservice->getContactsByUserId(man_id, flag);
                    printVector(res);
                    cout << "Enter id of contact: ";
                    cin >> contactId;
                    cin.ignore();
                    cout << "Enter new info: ";
                    string info;
                    getline(cin, info);
                    cservice->updateContact(contactId, flag, info);
                    break;
                }
                case 4:
                {
                    cin.ignore();
                    cout << "Enter new name: ";
                    string name;
                    getline(cin, name);
                    cout << "Enter new last name: ";
                    string lastName;
                    getline(cin, lastName);
                    cout << "Enter new patronymic: ";
                    string patr;
                    getline(cin, patr);
                    cout << "Enter new comment: ";
                    string comment;
                    getline(cin, comment);
                    service->updateById(man_id, name, lastName, patr, comment   );
                    break;
                }
                case 5:
                {
                    cservice->removeContactByUserId(man_id);
                    cout << "Deleted"<<endl;
                    break;
                }
                default:
                {
                    cout << "Uncorrect menu option"<<endl;
                    break;
                }
                }
            }
            catch (const char * err)
            {
                cerr << err << endl;
                return 1;
            }
            break;
        }
        case 3:
        {
            printVector(v);
            cout << "Enter id of user: ";
            cin >> man_id;
            service->deleteById(man_id);
            break;
        }
        case 4:
        {
            cout << "Enter string to find (will find over all params) ";
            string findInfo;
            getline(cin, findInfo);
            vector<PeopleEntity> ans = service->find(findInfo);
            printVector(ans);
            break;
        }
        case 5:
        {
            v = service->getAll();
            printVector(v);
            break;
        }
        case 6:
        {
            string name, lastName, patr, comment;
            cin.ignore();
            cout<<"Enter name of new user: ";
            getline(cin, name);
            cout<<"Enter last name of new user: ";
            getline(cin, lastName);
            cout<<"Enter patronymic of new user: ";
            getline(cin, patr);
            cout<<"Enter comment of new user: ";
            getline(cin, comment);
            service->add(name, lastName, patr, comment);
            break;
        }
        case 7:
        {
            return 0;
            break;
        }
        default:
        {
            cout << "Uncorrect menu option"<<endl;
            break;
        }
        }
    }
    return 0;
}
