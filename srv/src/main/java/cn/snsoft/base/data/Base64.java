package cn.snsoft.base.data;

import java.io.*;
import cn.snsoft.file.util.IOStreamUtils;

/*
 snsoft.commons.util.Base64
 */
final public class Base64 
{
	private Base64(){}
	final static public String encode(byte a[])//
	{
		try {
	        Encoder encode = new Encoder();
	        encode.write(a);
	        encode.close();
	        return encode.getAsString();
		} catch(  IOException ex)
		{
			throw new RuntimeException(ex);
		}
	}
	
	final static public String encodeString(String text,String charset) throws IOException
    {
    	return encode(charset==null?text.getBytes():text.getBytes(charset));
    }
	
	final static public byte[] decode(String text) throws java.io.IOException
    {
        return IOStreamUtils.toByteArray(new Decoder(text));
    }
    
	final static public String decodeAsText(String text,String cherSet) throws java.io.IOException
    {
    	return new String(IOStreamUtils.toByteArray(new Decoder(text)),cherSet);
    }
    
	final static public byte[] decode(String text,boolean skipBlank) throws java.io.IOException
    {
        return IOStreamUtils.toByteArray(new Decoder(text,skipBlank));
    }		
	
    /*
     *  参见 QDecoderStream
    */
    static public byte[] decodeQ(String text) // throws java.io.IOException
    {
        //“=68=65=6C=6C=6F”
        final int ltext = text.length();
        byte a[] = new byte[ltext]; int n = 0;
        for(int i=0;i<ltext;i++)
        {
            char c = text.charAt(i);
            if( c=='=' )
            {
                int c1 = toHex(text.charAt(++i));
                int c2 = toHex(text.charAt(++i));
                if( c1<0 || c2<0 )
                    throw new java.lang.IllegalArgumentException(text);
                a[n++] = (byte)((c1<<4)|c2);
                //if( c1>='0'
            } else
            {
                a[n++] = c=='_' ? (byte)' ' : (byte)c;
            }
        }
        byte newa[] = new byte[n]; System.arraycopy(a,0,newa,0,n);
        return newa;
    }	
	
	static public class Encoder extends OutputStream
	{
		//final StringBuffer  buffer = new StringBuffer();
		//final ByteArrayOutputStream os = new ByteArrayOutputStream() ;
	    private final java.io.OutputStream os;
		int remainder = 0;  int bitsRemainder = 0;
		int outCount = 0;
		public Encoder()
		{
	            os = new ByteArrayOutputStream() ;
		}
		public Encoder(java.io.OutputStream os)
		{
	            this.os = os;
		}
	    /**
	     * Writes the specified byte to this output stream. The general
	     * contract for <code>write</code> is that one byte is written
	     * to the output stream. The byte to be written is the eight
	     * low-order bits of the argument <code>b</code>. The 24
	     * high-order bits of <code>b</code> are ignored.
	     * <p>
	     * Subclasses of <code>OutputStream</code> must provide an
	     * implementation for this method.
	     *
	     * @param      b   the <code>byte</code>.
	     * @exception  IOException  if an I/O error occurs. In particular,
	     *             an <code>IOException</code> may be thrown if the
	     *             output stream has been closed.
	     */
		@Override
		public void write(int indata) throws IOException
		{
			indata &= 0xff;
			if( closed ) // || indata>255
				throw new IOException();
			for(;;)
			{
				if(bitsRemainder>=6)
				{
					os.write(toBase64Code((remainder>>(bitsRemainder-6))&63));
					//buffer.append((char)toBase64Code((remainder>>(bitsRemainder-6))&63));
					//super.write(toBase64Code((remainder>>(bitsRemainder-6))&63));
					outCount++;
					bitsRemainder -= 6;
					continue;
				}
				// int indata = is.read();
				if( indata<0 ) break;
				remainder = (remainder<<8)|(indata&0xff);  bitsRemainder+=8;
				indata = -1;
			}
		}
		boolean closed;
		@Override
		public void close() throws IOException
		{
	        if( !closed )
	        {
			if( bitsRemainder>0 ) // bitsRemainder<6
			{
				os.write(toBase64Code((remainder<<(6-bitsRemainder))&63));
				//buffer.append((char)toBase64Code((remainder<<(6-bitsRemainder))&63));
				//super.write(toBase64Code((remainder<<(6-bitsRemainder))&63));
				outCount++;
			}
			for(;(outCount&3)!=0;outCount++)
				//buffer.append('=');
				os.write('=');
			os.close();
			closed = true;
	        }
		}
		public static final int toBase64Code(int x)
		{
			if(x<0 || x>=64)
				throw new  IllegalArgumentException();
			if(x<=25) return (char)('A'+x);
			if(x<=51) return (char)('a'+x-26);
			if(x<=61) return (char)('0'+x-52);
			if(x==62) return '+';
			if(x==63) return '/';
			return '=';
		}
	    public byte[] toByteArray()  throws IOException
	    {
	        if( !closed )
	            throw new IOException();
	        if( !(os instanceof ByteArrayOutputStream) )
	            throw new IOException();
	        return ((ByteArrayOutputStream)os).toByteArray();//buffer.toString();
	    }
		public String getAsString()  throws IOException
		{
			if( !closed )
				throw new IOException();
	        if( !(os instanceof ByteArrayOutputStream) )
	            throw new IOException();
			return new String(((ByteArrayOutputStream)os).toByteArray(),"8859_1");//buffer.toString();
		}
		/*
		public byte[] getAsByteArray()  throws IOException
		{
			if( !closed )
				throw new IOException();
			return os.toByteArray();//buffer.toString();
		}
		*/
	} // Encoder

	static public class Decoder extends InputStream
	{
		int remainder = 0;  int bitsRemainder = 0;
		int outCount = 0;
		final InputStream inputStream;
		public Decoder(InputStream is)
		{
			this.inputStream = is;
		}
		public Decoder(byte[] ba)
		{
			this( new ByteArrayInputStream(ba) );
		}
		//final String text ;  int iText ;
		//final int  lenText ;
		public Decoder(String text)
		{
			//this.text = text;
			//lenText = text.length();
			this( new ByteArrayInputStream(text.getBytes()) );
		}
		public Decoder(String text,boolean skipBlank)
		{
			//this.text = text;
			//lenText = text.length();
			this( new ByteArrayInputStream(text.getBytes()) );
			this.skipBlank = skipBlank;
		}
		
		boolean skipBlank;
		public void setSkipBlank(boolean skipBlank)
		{
			this.skipBlank = skipBlank;
		}
		
		/**
		 * Reads the next byte of data from the input stream. The value byte is
		 * returned as an <code>int</code> in the range <code>0</code> to
		 * <code>255</code>. If no byte is available because the end of the stream
		 * has been reached, the value <code>-1</code> is returned. This method
		 * blocks until input data is available, the end of the stream is detected,
		 * or an exception is thrown.
	     *
	     * <p> A subclass must provide an implementation of this method.
	     *
	     * @return     the next byte of data, or <code>-1</code> if the end of the
	     *             stream is reached.
	     * @exception  IOException  if an I/O error occurs.
	     */
		@Override
		public int read() throws IOException
		{
		   for(;;)
		   {
				if(bitsRemainder>=8)
				{
					int c = remainder>>(bitsRemainder-8); //os.write(remainder>>(bitsRemainder-8));
					outCount++;
					bitsRemainder -= 8;
					return c&0xff; //continue;
				}
			//	if( iText>=lenText )
			//		return -1;
				int indata;
				if( skipBlank )
				{
					for(;;)
					{
						indata = inputStream.read(); //text.charAt(iText++); //is.read();
						if( indata<0 || indata>' ')
							break;
					}
				} else
				{
					indata = inputStream.read(); //text.charAt(iText++); //is.read();
				}
				if( indata<0 ) 
					return -1;//break;
				if( indata=='=' )
					continue;
				remainder = (remainder<<6)|fromBase64Code(indata);  bitsRemainder+=6;
		   }
		   //return -1;
		}
		
		//private int
		public static final int fromBase64Code(int x)
		{
			if(x>='A' && x<='Z') return x-'A';
			if(x>='a' && x<='z') return x-'a'+26;
			if(x>='0' && x<='9') return x-'0'+52;
			if(x=='+') return 62;
			if(x=='/') return 63;
			throw new  IllegalArgumentException("code="+(char)x+'('+x+')');
		}
//	    static public void main(String args[])
//	    {
////	        String s =  new String( decodeQ("=68=65=6C=6C=6Fabc") );
	//// System.err.println(s);
//	    }
	}
	
    static private int toHex(char c1)
    {
        if( c1>='0'&&c1<='9')
            return c1-'0';
        if( c1>='A'&&c1<='F' )
            return c1-'A'+10;
        if( c1>='a'&&c1<='f' )
            return c1-'a'+10;
        return -1;
    }
 
}
