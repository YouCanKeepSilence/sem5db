all: embedded

%.c: %.ec
	ecpg $<
	
%.o: %.c
	gcc -g -O0 -Wall -Werror -c -I/usr/include/postgresql $<
	
embedded: embedded.o main.o
	gcc -o $@ $^ -lecpg
	
clean:
	rm -rf *.o embedded *~ embedded.c