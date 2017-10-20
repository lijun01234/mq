package cn.snsoft.util;


public class ArrayIterator<E> implements java.util.Iterator<E>
{
    final Object a;
    final int startIndex, endIndex;
    public ArrayIterator(Object a)
    {
        this.a = a;
        this.i = this.startIndex = 0;
        this.endIndex = a==null ? 0 : java.lang.reflect.Array.getLength(a);
    }
    
    public ArrayIterator(Object a,int startIndex,int endIndex)
    {
        this.a = a;
        this.i = this.startIndex = startIndex;
        this.endIndex = endIndex;
    }
    
    public void reset() {
        this.i = this.startIndex;
    }

    int i ;
    @Override
	public boolean hasNext()
    {
        return i < endIndex;//a.length;
    }
    @Override
	public E next()
    {
        return i<endIndex ? (E)java.lang.reflect.Array.get(a,i++) : null;
    }
    @Override
	public void remove()
    {
        throw new java.lang.UnsupportedOperationException();
    }
    //
    //
    //
//    static public void main(String args[])
//    {
//        Object a[] = {1,2,3,4,5};
//        for(ArrayIterator i=new ArrayIterator(a);i.hasNext();)
//        {
//            System.out.println(i.next());
//        }
//    }
}
