package ar.edu.unq.apc.webService.dto;

public class AttributeDTO {

    private String name;

    private String value;


    public AttributeDTO() {
    }

    public AttributeDTO(String name, String value) {
        this.name = name;
        this.value = value;
    }


    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    
}
