/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author judit
 */
public interface List<E> {

    boolean addLast(E e);

    boolean addFirst(E e);

    E removeFirst(); // Lo saca y lo devuelve

    E removeLast();

    E get(int index);

    boolean isEmpty();

    int size();

    // Ver como hacer clear
    boolean add(int index, E e);

    E remove(int index);

    boolean contains(E e); // revisa si existe un elemento en la lista, la data se debe comparar con
    // equals.

    List<E> slicing(int start, int end); // devuelve una nueva lista con los elementos que estan en el rango de start y
    // end, sin incluir el end.
}
