package ms.core.persona.cliente.enums;

public enum Genero {
    MASCULINO("Masculino"),
    FEMENINO("Femenino");

    private final String descripcion;

    Genero(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
