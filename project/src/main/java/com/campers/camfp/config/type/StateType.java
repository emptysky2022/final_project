package com.campers.camfp.config.type;

public enum StateType {
	PAY_END("결제완료"),
	SHIP_READY("배송준비중"),
	SHIPPING("배송중"),
	SHIP_COMP("배송완료");

	private final String text;

    StateType(String text) {
        this.text = text;
    }

    public String getStatus() {
        return text;
    }
    
    public static StateType fromText(String text) {
        for (StateType status : StateType.values()) {
            if (status.getStatus().equalsIgnoreCase(text)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No StateType with text " + text);
    }

	@Override
	public String toString() {
		return this.name();
	}
	
    
}
