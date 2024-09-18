package ar.edu.unq.apc.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AttributeTest {

    private Attribute anyAttribute;

    @BeforeEach
    void init(){
        this.anyAttribute = new Attribute();
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
