package serializable;

import java.io.InvalidClassException;
import java.io.Serializable;

public class Person implements Serializable{
	
	/**
	 * The serialization runtime associates with each serializable class a version
	 * number, called a serialVersionUID, which is used during deserialization to
	 * verify that the sender and receiver of a serialized object have loaded
	 * classes for that object that are compatible with respect to serialization.
	 * If the receiver has loaded a class for the object that has a different
	 * serialVersionUID than that of the corresponding sender's class, then
	 * deserialization will result in an {@link InvalidClassException}.  A
	 * serializable class can declare its own serialVersionUID explicitly by
	 * declaring a field named {@code "serialVersionUID"} that must be static,
	 * final, and of type {@code long}:
	 * 
	 *  * If a serializable class does not explicitly declare a serialVersionUID, then
	 * the serialization runtime will calculate a default serialVersionUID value
	 * for that class based on various aspects of the class, as described in the
	 * Java Object Serialization Specification.  This specification defines the
	 * serialVersionUID of an enum type to be 0L. However, it is <em>strongly
	 * recommended</em> that all serializable classes other than enum types explicitly declare
	 * serialVersionUID values, since the default serialVersionUID computation is
	 * highly sensitive to class details that may vary depending on compiler
	 * implementations, and can thus result in unexpected
	 * {@code InvalidClassException}s during deserialization.  Therefore, to
	 * guarantee a consistent serialVersionUID value across different java compiler
	 * implementations, a serializable class must declare an explicit
	 * serialVersionUID value.  It is also strongly advised that explicit
	 * serialVersionUID declarations use the {@code private} modifier where
	 * possible, since such declarations apply only to the immediately declaring
	 * class--serialVersionUID fields are not useful as inherited members. Array
	 * classes cannot declare an explicit serialVersionUID, so they always have
	 * the default computed value, but the requirement for matching
	 * serialVersionUID values is waived for array classes.
	*/
	
	//best practice is to declare its own serialVersionUID explicitly. see reason above
	private static final long serialVersionUID = 8297976723952959236L;
	private String name;
	private int age;
	
	public Person() {
		
	}
	
	public Person(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}

	
}
