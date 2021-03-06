package com.hover.starter;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.hover.starter.actions.data.HoverActionVariable;
import com.hover.starter.actions.data.HoverActionVariableDao;
import com.hover.starter.actions.data.HoverAction;
import com.hover.starter.actions.data.HoverActionDao;
import com.hover.starter.actions.data.HoverTransaction;
import com.hover.starter.actions.data.HoverTransactionDao;
import com.hover.starter.workers.GetHoverActionsWorker;

import java.util.List;


public class HoverRepository {

    private HoverActionDao mActionDao;
    private HoverTransactionDao mTransactionDao;
    private HoverActionVariableDao mActionVariableDao;
    private LiveData<List<HoverAction>> mAllActions;
    private WorkManager mWorkManager;


    public HoverRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mActionDao = db.actionDao();
        mTransactionDao = db.transactionDao();
        mActionVariableDao = db.actionTransactionDao();
        mAllActions = mActionDao.getAllActions();
        mWorkManager = WorkManager.getInstance();
    }

    public LiveData<List<HoverAction>> getAllActions() {
        return mAllActions;
    }

    public HoverAction getAction(String actionId) {
        return mActionDao.getAction(actionId);
    }

    public HoverAction getAnyAction() {
        return mActionDao.getAnyAction();
    }

    public void loadActions() {
        mWorkManager.enqueue(OneTimeWorkRequest.from(GetHoverActionsWorker.class));
    }

    public LiveData<List<HoverTransaction>> getAllTransactionsByActionId(String actionId) {
        return mTransactionDao.getTransactionsByActionId(actionId);
    }

    public void insertTransaction(HoverTransaction transaction) {
        mTransactionDao.insert(transaction);
    }

    public void updateTransaction(HoverTransaction transaction) {
        mTransactionDao.update(transaction);
    }

    public LiveData<List<HoverTransaction>> getAllTransactions() {
        return mTransactionDao.getAllTransactions();
    }

    public HoverTransaction getTransaction(String uuid) {
        return mTransactionDao.getTransaction(uuid);
    }

    public LiveData<List<HoverActionVariable>> getAllActionVariablesByActionId(String action_id) {
        return mActionVariableDao.getAllActionVariablesByActionId(action_id);
    }

    public void insertActionVariable(HoverActionVariable actionVariable) {
        mActionVariableDao.insert(actionVariable);
    }
}


