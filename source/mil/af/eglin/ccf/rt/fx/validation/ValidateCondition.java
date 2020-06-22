package mil.af.eglin.ccf.rt.fx.validation;

/**
 * The validation of a {@link ValidableControl ValidableControl} can be triggered using {@code ValidateCondition}
 * <ul>
 * <li>MANUAL: The control is only validated when explicitly called.</li>
 * <li>UNFOCUS: The control is unfocused.</li>
 * <li>CHANGED: The value of the control has changed.</li>
 * </ul>
 * <p>
 */
public enum ValidateCondition
{
    MANUAL,
    UNFOCUS,
    CHANGED;
}
