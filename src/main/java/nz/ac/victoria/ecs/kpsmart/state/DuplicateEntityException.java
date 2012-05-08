package nz.ac.victoria.ecs.kpsmart.state;

/**
 * Indecates that an entity was non-unique with respect to it's unique keys
 * 
 * @author hodderdani
 *
 */
public class DuplicateEntityException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;

	public DuplicateEntityException() {
		super();
	}

	public DuplicateEntityException(String s) {
		super(s);
	}

	public DuplicateEntityException(Throwable cause) {
		super(cause);
	}

	public DuplicateEntityException(String message, Throwable cause) {
		super(message, cause);
	}
}
