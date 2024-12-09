package ar.edu.unq.apc.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AttributeTest {

    private Attribute anyAttribute;

    @BeforeEach
    void init(){
        this.anyAttribute = new Attribute();
    }

    @Test
    void aNewAttributeHasAName(){
        Attribute newAttribute = new Attribute("Material", "Plastico");

        assertEquals("Plastico", newAttribute.getValue());
        assertEquals("Material", newAttribute.getName());
    }

    @Test
    void aNewAttributeHasAValue(){
        Attribute newAttribute = new Attribute("Material", "Plastico");

        assertEquals("Plastico", newAttribute.getValue());
    }


    @Test
    void whenItSetsTheNameOfAnyAttributeThenItReturnsANewNameTest() {
        anyAttribute.setName("Condicion");

        assertEquals(anyAttribute.getName(), "Condicion");
    }

    @Test
    void whenItSetsTheValueOfAnyAttributeThenItReturnsANewValueTest() {
        anyAttribute.setValue("Usado");

        assertEquals(anyAttribute.getValue(), "Usado");
    }
}
