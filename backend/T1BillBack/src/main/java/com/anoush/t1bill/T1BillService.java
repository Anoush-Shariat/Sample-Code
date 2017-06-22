/**
 * 
 */
package com.anoush.t1bill;

/**
 * @author Anoush
 *
 */
public class T1BillService {
	
	private String code;
	private String description;  //
	private String status;       // "A", "D", "S"
	private String cost;         // 
	private String type;         // P for provision or otherwise
	private String startDate;
	private String endDate;
	
	//Future expansion
	
	
	public T1BillService(String code, String status, String type, String cost, String desc) {
		this.code = code;
		this.status = status;
		this.type = type;
		this.cost = cost;
		this.description = desc;
	}
	
	public T1BillService(String code, String status, String type, String cost, String desc, String sDate, String eDate) {
		this.code = code;
		this.status = status;
		this.type = type;
		this.cost = cost;
		this.description = desc;
		this.startDate = sDate;
		this.endDate = eDate;
	}
	
	public T1BillService() {
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof T1BillService))
			return false;
		T1BillService other = (T1BillService) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	public String getPrice() {
		return cost;
	}
	
	public void setPrice(String cost) {
		this.cost = cost;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    public String getType() {
    	return type;
    }
    
    public void setType(String prov) {
    	this.type = prov;
    }
    
    public String getEndDate() {
    	return endDate;
    }
    
    public void setEndDate(String endDate){
    	this.endDate = endDate;
    }
    
    public String getStartDate() {
    	return startDate;
    }
    
    public void setStartDate(String startDate) {
    	this.startDate = startDate;
    }
    
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append("{code: ").append(getCode())
    	  .append(" type: ").append(getType())
    	  .append(" status: ").append(getStatus())
    	  .append(" desc: ").append(getDescription())
    	  .append("}");
    	return sb.toString();
    }
   
}
