package com.anoush.t1bill;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class T1BillServiceUtility {

	public T1BillServiceUtility() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * @return HashSet<T1BillService> services to be deleted and de-provisioned
	 * @param Set<T1BillService> old services previously assigned and provisioned
	 * @param Set<T1BillService> new services being assigned and provisioned if applicable 
	 */
	public static Set<T1BillService> getDeletedServices(Set<T1BillService> oldServices, 
															Set<T1BillService> newServices) {
		
		// We could probably do a oldServices.removeAll(newServices) and 
		// get what we want but let's leave passed services intact and in their 
		// original state
		Set<T1BillService> deletedServices = new HashSet<>();
		if (newServices.containsAll(oldServices)) {
			return Collections.emptySet(); 
		} else {
			for (T1BillService srvc : oldServices) {
				if (!newServices.contains(srvc)) {
					deletedServices.add(srvc);
				}
			}
		}
		return deletedServices;
	}

	/*
	 * @return Set<T1BillService> new services to be added and provisioned
	 * @param Set<T1BillService> old services previously assigned and provisioned
	 * @param Set<T1BillService> new services being assigned and provisioned 
	 */
	public static Set<T1BillService> getAddedServices(Set<T1BillService> oldServices, 
															Set<T1BillService> newServices) {
		// we could probably do a newServices.removeAll(oldServices) and 
		// get what we want but let's leave passed parameters in their state
		//return newServices.removeAll(oldServices);
		Set<T1BillService> addedServices = new HashSet<>();
		for (T1BillService srvc: newServices) {
		    if (!oldServices.contains(srvc)) {
		    	addedServices.add(srvc);
		    }
		}
		return addedServices;
	}

	/*
	 * This first release does not do provisioning like suspend/activate/deactivate a service but
	 * only adding and deleting services. Use this as the template for finer provisioning of services.
	 * Provisionable services have their type = "P"
	 * We call this on addedServices or deletedService sets. Just a convenient method
	 */
	public static Set<T1BillService> getProvisionableServices(Set<T1BillService> newServices ) { 
															 
		Set<T1BillService> provisionServices = new HashSet<>();
		for (T1BillService srvc : newServices) {
			if (srvc.getType().toUpperCase().equals("P")) {
				provisionServices.add(srvc);
			}
		}
		return provisionServices;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}
