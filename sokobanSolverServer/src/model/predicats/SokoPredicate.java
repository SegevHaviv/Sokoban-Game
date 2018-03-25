package model.predicats;

import predicates.Predicate;

public class SokoPredicate extends Predicate {

	public SokoPredicate(String type, String id, String value) {
		super(type, id, value);
	}
	
	@Override
	public boolean contradicts(Predicate p) {
		return super.contradicts(p) || (!id.equals(p.getId()) && value.equals(p.getValue()));
	}

}
