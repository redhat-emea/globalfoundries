package com.redhat.emea.globalfoundries.ejb;

import com.redhat.emea.globalfoundries.util.Coordinator;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.transaction.SystemException;

@Singleton
public class BusinessBean implements Business {

    @EJB
    private Coordinator coordinator;

    @Resource
    private EJBContext ejbContext;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void doTask() throws EJBException {
        System.out.println("BusinessBean.doTask");
        System.out.println("BusinessBean.doTask - coordinator.isRunning(): " + coordinator.isRunning());
        System.out.println("BusinessBean.doTask - ejbContext.getUserTransaction(): " + ejbContext.getUserTransaction());
        try {
            System.out.println("BusinessBean.doTask - ejbContext.getUserTransaction().getStatus(): " + ejbContext.getUserTransaction().getStatus());
        } catch (SystemException e) {
            e.printStackTrace();
        }

        int errs = 3;
        int count = 0;
        while (coordinator.isRunning()) {
            try {
                System.out.println("BusinessBean.doTask - Doing something long");
                Thread.currentThread().sleep(2000l);
                System.out.println("BusinessBean.doTask - Done");
                count++;
                if (count >= errs) {
                    throw new IllegalArgumentException("Invalid values for EJB");
                }
            } catch (InterruptedException ie) {
                System.err.println("BusinessBean.doTask - I got interrupted, but I can handle that!");
                //e.printStackTrace();
                throw new EJBException(ie);
            } catch (Exception e) {
                System.err.println("BusinessBean.ROLLBACK");
                throw new EJBException(e);
            } finally {
                ejbContext.setRollbackOnly();
            }
        }
    }
}
