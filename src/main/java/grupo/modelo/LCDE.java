package grupo.modelo;


import java.util.Comparator;
import java.util.Iterator;


public class LCDE<E> implements Iterable<E>{
    private Node<E> header;
    private Node<E> last;
    private int lenght;
        
    public boolean contains(E e) {
        if (e == null) {
            return false;
        }
        Node<E> tmp = header;
        for (int i = 0; i < getLenght(); i++) {
            if (tmp.content.equals(e)) {
                return true;
            }
            tmp = tmp.next;

        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new CustomIterator<>(this);
            
    }
          
    private class CustomIterator<E> implements Iterator<E> {
        int cursor = 0;
        LCDE<E> lista;
        
        CustomIterator (LCDE<E> lista){
            this.lista = lista;
        }

        @Override
        public boolean hasNext() {
            return cursor != lista.getLenght();
        }

        @Override
        public E next() {
            E elemento = lista.getContentIndex(cursor);
            cursor++;
            return elemento;
        }
    }
 
    private class Node<E> {
        private Node<E> next;
        private Node<E> previous;
        private final E content;
        
        public Node(E content){
            this.content = content;
            this.next = null;
            this.previous = null;
        }
        
        public E getContent(){
            return content;
        }
        public Node<E> getNext(){
            return next;
        }
        public Node<E> getPrevious(){
            return previous;
        }
    }
    
    public LCDE(){
        this.last = null;
        this.header = null;
        this.lenght = 0;
    }
    
    public boolean isEmpty(){
        return lenght==0;
    }
    
    public void addLast(E content){
        Node<E> nuevoNodo = new Node<>(content);
        if(lenght==0){
            nuevoNodo.previous = nuevoNodo;
            nuevoNodo.next = nuevoNodo;
            this.last = nuevoNodo;
            this.header = nuevoNodo;
            lenght++;
            return;
        }
        nuevoNodo.next = header;
        header.previous = nuevoNodo;
        this.last.next = nuevoNodo;
        nuevoNodo.previous = last;
        this.last = nuevoNodo;
        lenght++;
    }
    
    public void addIndex(int index, E content){
        if(index<0 || index>lenght){
            throw new IndexOutOfBoundsException("Indice no admitido");
        }
        if(index==0){
            addFirst(content);
            return;
        }
        // OJO: aqui yo quiero que se pueda poner ultimoIndice+1 para agregar al final del todo un elemento si lo quisiese con este metodo
        // No es un error, en caso que no pidiera eso solo pondria el >= en la exception de arriba
        if(index==lenght){  
            addLast(content);
            return;
        }
        Node<E> nuevoNodo = new Node(content);
        if(index+1<=(lenght)/2){
            Node<E> preNodo = this.recorrerDerecho(index);
            Node<E> postNodo = this.recorrerDerecho(index+1);
            preNodo.next = nuevoNodo;
            nuevoNodo.previous = preNodo;
            postNodo.previous = nuevoNodo;
            nuevoNodo.next = postNodo;
        }else{
            Node<E> preNodo = this.recorrerReversa(index-1);
            Node<E> postNodo = this.recorrerReversa(index);
            preNodo.next = nuevoNodo;
            nuevoNodo.previous = preNodo;
            postNodo.previous = nuevoNodo;
            nuevoNodo.next = postNodo;
        }
        lenght++;
    }   
    
    public void addFirst(E content){
        if(content==null){
            return; // No agrega nada porque el contenido es nulo, igual podria retornar un false o una exception pero yo no deseo eso
        }
        Node<E> nuevoNodo = new Node<>(content);
        nuevoNodo.next = header;
        nuevoNodo.previous = last;
        header.previous = nuevoNodo;
        this.header = nuevoNodo;
        last.next = nuevoNodo;
        lenght++;
    }
    
    public void removeLast(){
        if(lenght==0){ return; }
        if(lenght==1){
            this.header=null;
            this.last=null;
            lenght--;
            return;
        }
        Node<E> penultimoNodo = last.previous;
        penultimoNodo.next = header;
        header.previous = penultimoNodo;
        this.last = penultimoNodo;
        lenght--;
    }
    
    public void removeIndex(int index){
        if(index<0 || index>=lenght){
            throw new IndexOutOfBoundsException("Indice no admitido");
        }
        if(index==0){ 
            removeFirst(); 
            return;
        }
        if(index==lenght-1){ 
            removeLast(); 
            return;
        }
        if(index+1<=(lenght)/2){
            Node<E> preNodo = this.recorrerDerecho(index);
            Node<E> postNodo = this.recorrerDerecho(index+2);
            preNodo.next = postNodo;
            postNodo.previous = preNodo;
        }else{
            Node<E> preNodo = this.recorrerReversa(index-1);
            Node<E> postNodo = this.recorrerReversa(index+1);
            preNodo.next = postNodo;
            postNodo.previous = preNodo;
        }
        lenght--;
    }
    
    public void removeFirst(){
        Node<E> segundoNodo = header.next;
        segundoNodo.previous = last;
        last.next = segundoNodo;
        header = segundoNodo;
        lenght--;
    }
    
    public E getContentLast(){
        return last.content;
    }
    
    public E getContentIndex(int index){
        if(index<0 || index>=lenght){
            throw new IndexOutOfBoundsException("Indice no admitido");
        }
        if(index==0){ 
            return getContentFirst();
        }
        if(index==lenght-1){ 
            return getContentLast();
        }
        if(index+1<=(lenght)/2){
            Node<E> nodo = this.recorrerDerecho(index);
            return nodo.next.content;
        }else{
            Node<E> nodo = this.recorrerReversa(index);
            return nodo.content;
        }
    }
    
    public E getContentFirst(){
        return header.content;
    }
    
    public int getLenght(){
        return lenght;
    }
    
    private Node<E> recorrerDerecho(int index){
        Node<E> nodo;
        int contador=0;
        for(nodo=this.last;contador<index;nodo=nodo.next,contador++){}
        return nodo;
    }
    
    private Node<E> recorrerReversa(int index){
        Node<E> nodo;
        int contador=this.lenght-1;
        for(nodo=this.last;contador>index;nodo=nodo.previous,contador--){}
        return nodo;
    }
    
    public void printElements(){
        Node<E> nodo;
        int contador=0;
        for(nodo=this.header;contador<lenght;nodo=nodo.next,contador++){
            System.out.println(nodo.content + " ");
        }
    }
    
    public void printElementsReverse(){
        Node<E> nodo;
        int contador=0;
        for(nodo=this.last;contador<lenght;nodo=nodo.previous,contador++){
            System.out.print(nodo.content + " ");
        }
    }
    
    public void verificacionAlDerecho(){
        Node<E> nodo;
        int contador=0;
        for(nodo=this.header;contador<15+lenght;nodo=nodo.next,contador++){
            System.out.print(nodo.content + " ");
        }
    }
    
    public void verificacionAlRevez(){
        Node<E> nodo;
        int contador=0;
        for(nodo=this.last;contador<15+lenght;nodo=nodo.previous,contador++){
            System.out.print(nodo.content + " ");
        }
    }
    
    public E find(Comparator<E> comp, E buscar){
        for(E elem: this){
            if(comp.compare(elem, buscar) == 0) return elem;
        }
        return null;
    }
    
}
