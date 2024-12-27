package com.bank.mapper;

import com.bank.Account;
import com.bank.Operation;
import com.bank.data.AccountEntity;
import com.bank.data.OperationEntity;

public class OperationEntityMapper {
    public static OperationEntity toOperationEntity(Operation op) {
        OperationEntity entity = new OperationEntity();

        entity.setId(op.getId());
        entity.setType(op.getType());
        entity.setValue(op.getValue());
        entity.setAccount(new AccountEntity(op.getAccount().getId()));

        return entity;
    }

    public static Operation toOperation(OperationEntity entity) {
        Operation op = new Operation();

        op.setId(entity.getId());
        op.setType(entity.getType());
        op.setValue(entity.getValue());
        op.setAccount(new Account(entity.getAccount().getId()));

        return op;
    }
}
