package org.eaSTars.z80asm.ast.parameter;

public class ConditionParameter extends Parameter {

	private Condition condition;
	
	public ConditionParameter() {
	}
	
	public ConditionParameter(Condition condition) {
		setCondition(condition);
	}
	
	@Override
	public String getAssembly() {
		return condition.getValue();
	}

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof ConditionParameter &&
				((condition == null && ((ConditionParameter)obj).getCondition() == null) ||
						(condition != null && condition == ((ConditionParameter)obj).getCondition()));
	}

}
