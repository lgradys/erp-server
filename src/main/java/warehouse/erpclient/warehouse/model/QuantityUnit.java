package warehouse.erpclient.warehouse.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QuantityUnit {

    LENGTH("metre", "m"),
    MASS("kilogram", "kg"),
    PIECES("pieces", "psc");

    private String unit;
    private String unitSymbol;

}
