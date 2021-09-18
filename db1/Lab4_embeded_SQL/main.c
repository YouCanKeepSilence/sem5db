#include <stdio.h>
#include <unistd.h>
#include <string.h>

#include "embedded.h"

int main(int argc, char **argv) 
{
  int status = connect("");
  
  if (status) 
  {
    fprintf(stderr, "Error connecting to database\r\n");
    return 1;
  }
  int sum;
  char prof[128];
  printf("---TASK A: \r\n");
  printf("Enter sum of agreement: ");
  scanf("%d", &sum);
  printf("Enter name of profession: ");
  scanf("%s", prof);
  printf("---RESULT: \r\n");
  status = taskA(sum, prof);

  if (status) 
  {
		fprintf(stderr, "Error in task a \r\n");
		return 1;
  }
  int benefit;
  char date[128];
  printf("---TASK B: \r\n");
  printf("Enter benefit of employer: ");
  scanf("%d", &benefit);
  printf("Enter date of agreement: ");
  scanf("%s", date);
  printf("---RESULT: \r\n");
  status = taskB(benefit, date);
  if (status) 
  {
		fprintf(stderr, "Error in task b \r\n");
		return 1;
  }

  int fee;
  char city[128];
  printf("---TASK C: \r\n");
  printf("Enter service fee of bureau: ");
  scanf("%d", &fee);
  printf("Enter city to ignore: ");
  scanf("%s", city);
  printf("---RESULT: \r\n");
  status = taskC(fee, city);
  if (status) 
  {
		fprintf(stderr, "Error in task c \r\n");
		return 1;
  }
  printf("---TASK D: \r\n");
  printf("---RESULT: \r\n");
  status = taskD();
  if (status) 
  {
		fprintf(stderr, "Error in task d \r\n");
		return 1;
  }
}