package warehouse.erpclient.warehouse.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PostalCodeValidator implements ConstraintValidator<PostalCode, String> {

    private final String POSTAL_CODE_PATTERN = "\\d{2}-\\d{3}";

    @Override
    public boolean isValid(String postalCode, ConstraintValidatorContext constraintValidatorContext) {
        return validate(postalCode);
    }

    private boolean validate(String postalCode) {
        Pattern pattern = Pattern.compile(POSTAL_CODE_PATTERN);
        return pattern.matcher(postalCode).matches();
    }

}
