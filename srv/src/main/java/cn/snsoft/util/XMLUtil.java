package cn.snsoft.util;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
public class XMLUtil
{
	private static final Map<ClassLoader,DocumentBuilderFactory>	DOCUMENT_BUILDER_FACTORIES	= Collections.synchronizedMap(new WeakHashMap<ClassLoader,DocumentBuilderFactory>());

	private static DocumentBuilderFactory getDocumentBuilderFactory()
	{
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		if (loader == null)
		{
			loader = XMLUtil.class.getClassLoader();
		}
		if (loader == null)
		{
			return DocumentBuilderFactory.newInstance();
		}
		DocumentBuilderFactory factory = DOCUMENT_BUILDER_FACTORIES.get(loader);
		if (factory == null)
		{
			factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DOCUMENT_BUILDER_FACTORIES.put(loader, factory);
		}
		return factory;
	}

	static public Document parse(java.io.InputStream is)
	{
		try
		{
			final org.w3c.dom.Document doc = getDocumentBuilderFactory().newDocumentBuilder().parse(is);
			doc.getDocumentElement().normalize();
			return doc;
		} catch (Throwable ex)
		{
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
	}

	public static final class ElementIterator implements Iterator<Element>
	{
		public ElementIterator(Element parent)
		{
			e = parent.getFirstChild();
		}

		boolean	eof;
		Node	e;

		@Override
		public boolean hasNext()
		{
			if (eof)
				return false;
			for (; e != null; e = e.getNextSibling())
			{
				if (e == null || e instanceof Element)
					break;
			}
			return e != null;
		}

		@Override
		public org.w3c.dom.Element next()
		{
			final Element v = (Element) e;
			if (e != null)
				e = e.getNextSibling();
			return v;
		}

		@Override
		public void remove()
		{
			throw new java.lang.UnsupportedOperationException();
		}
	}

	final public static Iterable<Element> iteratorChileElements(final Element parent)
	{
		return new Iterable<Element>()
		{
			@Override
			public Iterator<Element> iterator()
			{
				return new ElementIterator(parent);
			}
		};
	}

	final static public java.util.List<org.w3c.dom.Element> getChildElements(final org.w3c.dom.Node parent)
	{
		final java.util.List<Element> v = new java.util.ArrayList<>();
		getChildElementsTo(parent, v);
		return v;
	}

	final static public void getChildElementsTo(final org.w3c.dom.Node parent, java.util.List<org.w3c.dom.Element> to)
	{
		for (org.w3c.dom.Node e = parent.getFirstChild(); e != null; e = e.getNextSibling())
		{
			if (e instanceof org.w3c.dom.Element)
			{
				to.add((org.w3c.dom.Element) e);
			}
		}
	}

	static public String removeAttribute(final org.w3c.dom.Element e, String name)
	{
		String val = e.getAttribute(name);
		e.removeAttribute(name);
		return val == null || val.length() == 0 ? null : val;
	}
}
