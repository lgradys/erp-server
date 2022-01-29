package warehouse.erpclient.warehouse.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PostalCodeValidator implements ConstraintValidator<PostalCode, String> {

    private final String POSTAL_CODE_PATTERN = "\\d{2}-\\d{3}";

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return validate(email);
    }

    private boolean validate(String email) {
        Pattern pattern = Pattern.compile(POSTAL_CODE_PATTERN);
        return pattern.matcher(email).matches();
    }
}
