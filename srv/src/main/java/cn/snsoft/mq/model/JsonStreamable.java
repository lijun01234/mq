package cn.snsoft.mq.model;

/*
  JSCode
 */
public interface JsonStreamable 
{
	// toMap  toJson 一般 其中之一 
  //  public void writeObject(XDataOutput dataOutput,java.io.Writer out);
    //java.util.Map toMap(); // 
	
    public Object  toJson();
}
