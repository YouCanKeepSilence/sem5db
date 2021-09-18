all:
	g++ *.cpp -I/usr/local/include/server -L/usr/local/lib/postgresql -o mmm.so
lib:
	g++ *.cpp -I/usr/local/include/server -L/usr/local/lib/postgresql -c
	g++ -bundle -flat_namespace -undefined suppress -o doglib.so *.o
clean:
	rm *.o *.so
