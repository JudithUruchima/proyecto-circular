/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 *
 * @author judit
 */
public class DoublyCircular<E> implements List<E>, Iterable<E> {

  private Node<E> last; // null por defecto
   private int current; // 0 por defecto

    // Inner class
    class Node<E> {

        E data; // null por defecto, pero necesito el vagon
        Node<E> next; // null por defecto
        Node<E> previous; // null por defecto

        Node(E data) {
            this.data = data;

        }
        // No necesito geters and seters porq es inner
        // Ni public porque solo lo uso en esta clase
    }

    @Override
    public boolean addLast(E e) {
        if (e == null) {
            return false;
        }
        Node<E> p = new Node<>(e);
        if (isEmpty()) {
            last = p;
            last.next = last;
            last.previous = last;
        } else {
            p.next = last.next;
            p.previous = last;
            last.next.previous = p;
            last.next = p;
            last = p;
        }
        current++;
        return true;
    }

    @Override
    public boolean addFirst(E e) {
        if (e == null) {
            return false;
        }
        Node<E> p = new Node<>(e); // Lo embarca en un vagon
        if (isEmpty()) {
            last = p;
            last.next = last;
            last.previous = last;
        } else {
            p.next = last.next;
            last.next.previous = p;
            last.next = p;
            p.previous = last;
        }
        current++;
        return true;
    }

    @Override
    public E removeFirst() {
        if (this.isEmpty()) {
            throw new UnsupportedOperationException("Lista Vacía");
        }
        E tmp = last.next.data; // increible
        if (last == last.next) { // Solo hay un elemento en la lista
            last = null;
        } else {
            last.next = last.next.next;
            last.next.next.previous = last;
        }
        current--;
        return tmp;
    }

    @Override
    public E removeLast() { // O(1)
        if (this.isEmpty()) {
            throw new UnsupportedOperationException("Lista Vacia");
        }
        E tmp = last.data; // increible
        if (last.previous == last) {
            last = null;
        } else {
            Node<E> p = last.previous;
            p.next = last.next;
            last.next.previous = p;
            last = p;
        }
        current--;
        return tmp;
    }

    @Override
    public E get(int index) {
        // Indice es la cantidad de desplazamientos que tengo que hacer con p
        if (this.isEmpty()) // lanzamos una excepcion
            throw new UnsupportedOperationException("Lista Vacía"); // Ocurre en tiempo de ejecucion
        if (index >= 0 && index < current) {
            Node<E> p = last.next;
            for (int i = 0; i < index; i++) {
                p = p.next;
            }
            return p.data;
        } else {
            throw new ArrayIndexOutOfBoundsException("Índice fuera del rango");
        }
    }

    @Override
    public boolean isEmpty() {
        return (last == null);
    }

    @Override
    public int size() {
        return current;
    }

    @Override
    public String toString() {
        if (this.isEmpty()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        Node<E> p = last.next; // Iniciamos desde el primer nodo
        do {
            sb.append(p.data);
            p = p.next;
            if (p != last.next) { // Evita agregar una coma después del último elemento
                sb.append(",");
            }
        } while (p != last.next); // Recorremos hasta volver al inicio

        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean add(int index, E e) {
        if (e == null) {
            return false;
        }
        if (this.isEmpty()) {
            throw new UnsupportedOperationException("Lista vacia");
        }
        if (index == 0) {
            addFirst(e);
            return true;
        }
        if (index == current) {
            addLast(e);
            return true;
        }
        if (index >= 0 && index < current) {
            Node<E> p = new Node<>(e);
            Node<E> tmp = last;
            for (int i = 0; i < index; i++) {
                tmp = tmp.next;
            }
            p.next = tmp.next;
            tmp.next.previous = p;
            p.previous = tmp;
            tmp.next = p;
            current++;
            return true;
        } else {
            throw new ArrayIndexOutOfBoundsException("Indice fuera del rango");
        }
    }

    @Override
    public E remove(int index) {
        if (this.isEmpty()) {
            throw new UnsupportedOperationException("Lista Vacia");
        }
        if (index < 0 || index >= current) {
            throw new ArrayIndexOutOfBoundsException("Indice fuera del rango");
        }
        E variableBorrada = get(index);
        if (index == 0) {
            removeFirst();
            return variableBorrada;
        }
        if (index == current - 1) {
            removeLast();
            return variableBorrada;
        }
        Node<E> tmp = last;
        for (int i = 0; i < index; i++) {
            tmp = tmp.next;
        }
        tmp.next = tmp.next.next;
        tmp.next.previous = tmp;
        current--;
        return variableBorrada;
    }

    @Override
    public boolean contains(E e) {
        if (e == null) {
            return false;
        }
        if (this.isEmpty()) {
            throw new UnsupportedOperationException("Lista vacía");
        }
        Node<E> p = last.next;
        do {
            if (e.equals(p.data)) {
                return true;
            }
            p = p.next;
        } while (p != last.next);
        return false;
    }

    @Override
    public List<E> slicing(int start, int end) { // sin incluir el end
        if (this.isEmpty()) {
            throw new UnsupportedOperationException("Lista Vacia");
        }
        if (start < 0 || end > (current) || start >= end) {
            throw new ArrayIndexOutOfBoundsException("Indice fuera del rango");
        }
        List<E> listaSecundaria = new DoublyCircular<>();
        Node<E> p = last.next;
        for (int i = 0; i < end; i++) {
            if (i >= start) {
                listaSecundaria.addLast(p.data);
            }
            p = p.next;
        }
        return listaSecundaria;
    }
    
    public void playLoop() { //se reprodzca en bucle
    if (isEmpty()) {
        System.out.println("Lista vacía.");
        return;
    }
    Node<E> temp = last.next;
    while (true) { // Bucle infinito
        System.out.println(temp.data);
        temp = temp.next; // Avanza al siguiente nodo

        try {
            Thread.sleep(2000); // Simula una pausa entre videos (2s)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            break;
        }
    }
}

    @Override
    public Iterator<E> iterator() {
        Iterator<E> it = new Iterator<E>() {
            Node<E> p = last.next;
            boolean first = true; // Para controlar la primera iteración

            @Override
            public boolean hasNext() {
                return first || p != last.next; // Detiene cuando vuelve al inicio
            }

            @Override
            public E next() {
                first = false; // Solo la primera vez será `true`
                E tmp = p.data;
                p = p.next; // Avanza al siguiente nodo
                return tmp;
            }
        };
        return it;
    }

    public ListIterator<E> listIterator2() { // hace next, previous, has next y has previous
        ListIterator<E> lit = new ListIterator<E>() {
            Node<E> p = last.next;
            boolean first = true;
            int indice = current-1;

            @Override
            public boolean hasNext() {
                return p!= null;
            }

            @Override
            public E next() {
                first = false; // Solo la primera vez será `true`
                E tmp = p.data;
                p = p.next; // Avanza al siguiente nodo
                indice++;
                return tmp;
            }

            @Override
            public boolean hasPrevious() {
                return p != null;
            }

            @Override
            public E previous() {
                if (!hasPrevious()) {
                    throw new NoSuchElementException("No hay más elementos previos");
                }
                p = p.previous;
                indice--;
                return p.data;
            }

            @Override
            public int nextIndex() { // la p arranca desde donde el usuario puso su indice con un for, while,etc. //
                // devuelve el indice siguiente de mi posicion, una variable de control
                // adicional la i para ver donde esta
                return indice+1;
            }

            @Override
            public int previousIndex() {
                return indice-1; 
            }

            @Override
            public void remove() { // Elimina donde esta el indice, solo elimina, a partir de p
                if (p == null) {
                    throw new IllegalStateException("Debe llamar a next() o previous() antes de remove()");
                }
                if (isEmpty()) {
                    throw new UnsupportedOperationException("Lista Vacia");
                }
                if (p == last.next) {
                    removeFirst();
                    return;
                }
                if (p == last) {
                    removeLast();
                    return;
                } else {
                    Node<E> prev = p.previous;
                    Node<E> next = p.next;

                    if (prev != last) {
                        prev.next = next;
                    }
                    if (next != last.next) {
                        next.previous = prev;
                    }
                    p = next;
                }
                current--;
            }

            @Override
            public void set(E e) { // Donde esta el iterador, cambia la data en el indice que recibe el programa
                if (e == null) {
                    throw new IllegalStateException("Ingrese un elemento valido");
                }
                if (isEmpty()) {
                    throw new UnsupportedOperationException("Lista Vacia");
                }
                p.data = e;
            }

            @Override
            public void add(E e) { // donde esta p, agrego el e, tener una variable de control como si es 0 es un
                // addfirst, pero p debe estar en mi indice
                if (e == null) {
                    throw new UnsupportedOperationException("Ingrese un elemento valido");
                }
                if (p == null) {
                    throw new IllegalStateException("Debe llamar a next() o previous() antes de remove()");
                }
                if (p == last.next) {
                    addFirst(e);
                    //last.next = last.next.previous;
                    return;
                }
                if (p == last) {
                    addLast(e);
                    last = last.next; 
                    return;
                } else {
                    Node<E> tmp = new Node<>(e);
                    tmp.previous = p.previous;
                    tmp.next = p;
                    p.previous.next = tmp;
                    p.previous = tmp;
                    
                    current++;
                    indice++;
                }
            }

            @Override
            public String toString() {
                if (isEmpty()) {
                    return "[]";
                }
                StringBuilder sb = new StringBuilder();
                sb.append("[");

                Node<E> p = last.next; // Iniciamos desde el primer nodo
                do {
                    sb.append(p.data);
                    p = p.next;
                    if (p != last.next) { // Evita agregar una coma después del último elemento
                        sb.append(",");
                    }
                } while (p != last.next); // Recorremos hasta volver al inicio

                sb.append("]");
                return sb.toString();
            }

        };
        return lit;
    }

    public Node<E> metodoP(int index) {
        if (index < 0 || index > current) { // Verificación de rango correcta
            throw new IndexOutOfBoundsException("Índice fuera del rango");
        }

        Node<E> p = last.next; // Comenzamos desde el primer nodo
        for (int i = 0; i < index; i++) { // Nos movemos hacia adelante
            p = p.next;
        }
        return p;
    }
    

    public ListIterator<E> listIterator(int index) { // hace next, previous, has next y has previous

        ListIterator<E> lit = new ListIterator<E>() {
            Node<E> p = metodoP(index);
            int indice = index;
            boolean first = true;

            @Override
            public boolean hasNext() {// ->Si
                return first || p != null;
            }

            @Override
            public E next() { // -> Si
                first = false; // Solo la primera vez será `true`
                E tmp = p.data;
                p = p.next; // Avanza al siguiente nodo
                indice++;
                return tmp;
            }

            @Override
            public boolean hasPrevious() {
                return p != null;
            }

            @Override
            public E previous() { // ->Si
                if (!hasPrevious()) {
                    throw new NoSuchElementException("No hay más elementos previos");
                }
                p = p.previous;
                indice--;
                return p.data;
            }

            @Override
            public int nextIndex() { // la p arranca desde donde el usuario puso su indice con un for, while,etc. //
                // devuelve el indice siguiente de mi posicion, una variable de control
                // adicional la i para ver donde esta -->Si
                return indice + 1;

            }

            @Override
            public int previousIndex() { // -->Si
                return indice - 1;
            }

            @Override
            public void remove() {
                if (p == null || index == current) {
                    throw new IllegalStateException("Debe llamar a next() o previous() antes de remove() o no indice fuera del rango.");
                }
                if (p == last.next) {
                    p = p.next; // Mover `p` al siguiente nodo antes de eliminar
                    removeFirst();
                } else if (p == last) {
                    p = p.previous; // Mover `p` al nodo anterior antes de eliminar
                    removeLast();
                } else {
                    Node<E> prev = p.previous;
                    Node<E> next = p.next;

                    prev.next = next; // Saltar el nodo `p`
                    next.previous = prev;

                    p = next; // Mover el iterador al siguiente nodo
                    current--;
                    return;
                }
            }

            @Override
            public void set(E e) {
                if (e == null) {
                    throw new IllegalArgumentException("Ingrese un elemento válido");
                }
                if (p == null || index == current) {
                    throw new IllegalStateException("Debe llamar a next() o previous() antes de set() o indice fuera de rango.");
                }
                p.data = e;
            }

            @Override
            public void add(E e) {
                if (e == null) {
                    throw new IllegalArgumentException("Ingrese un elemento válido");
                }
                if (p == null) {
                    throw new IllegalStateException("Debe llamar a next() o previous() antes de add()");
                }
                if (p == last.next && index != current) { // Si `p` está en el primer nodo
                    addFirst(e);
                    p = last.next; // Mover `p` al nuevo nodo insertado
                    return;
                }
                if (p == last || index == current) { // Si `p` está en el último nodo
                    addLast(e);
                    p = last; // Mover `p` al nuevo nodo insertado
                    return;
                }
                // Inserción en el medio de la lista
                Node<E> tmp = new Node<>(e);
                tmp.previous = p.previous;
                tmp.next = p;
                p.previous.next = tmp;
                p.previous = tmp;

                p = tmp; // Mover `p` al nuevo nodo insertado
                current++;
            }

            @Override
            public String toString() {
                if (isEmpty()) {
                    return "[]";
                }
                StringBuilder sb = new StringBuilder();
                sb.append("[");

                Node<E> p = last.next; // Iniciamos desde el primer nodo
                do {
                    sb.append(p.data);
                    p = p.next;
                    if (p != last.next) { // Evita agregar una coma después del último elemento
                        sb.append(",");
                    }
                } while (p != last.next); // Recorremos hasta volver al inicio

                sb.append("]");
                return sb.toString();
            }
        };
        return lit;
    }

}

