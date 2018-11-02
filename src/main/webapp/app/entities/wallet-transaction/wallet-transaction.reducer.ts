import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IWalletTransaction, defaultValue } from 'app/shared/model/wallet-transaction.model';

export const ACTION_TYPES = {
  SEARCH_WALLETTRANSACTIONS: 'walletTransaction/SEARCH_WALLETTRANSACTIONS',
  FETCH_WALLETTRANSACTION_LIST: 'walletTransaction/FETCH_WALLETTRANSACTION_LIST',
  FETCH_WALLETTRANSACTION: 'walletTransaction/FETCH_WALLETTRANSACTION',
  CREATE_WALLETTRANSACTION: 'walletTransaction/CREATE_WALLETTRANSACTION',
  UPDATE_WALLETTRANSACTION: 'walletTransaction/UPDATE_WALLETTRANSACTION',
  DELETE_WALLETTRANSACTION: 'walletTransaction/DELETE_WALLETTRANSACTION',
  RESET: 'walletTransaction/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IWalletTransaction>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type WalletTransactionState = Readonly<typeof initialState>;

// Reducer

export default (state: WalletTransactionState = initialState, action): WalletTransactionState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_WALLETTRANSACTIONS):
    case REQUEST(ACTION_TYPES.FETCH_WALLETTRANSACTION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_WALLETTRANSACTION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_WALLETTRANSACTION):
    case REQUEST(ACTION_TYPES.UPDATE_WALLETTRANSACTION):
    case REQUEST(ACTION_TYPES.DELETE_WALLETTRANSACTION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_WALLETTRANSACTIONS):
    case FAILURE(ACTION_TYPES.FETCH_WALLETTRANSACTION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_WALLETTRANSACTION):
    case FAILURE(ACTION_TYPES.CREATE_WALLETTRANSACTION):
    case FAILURE(ACTION_TYPES.UPDATE_WALLETTRANSACTION):
    case FAILURE(ACTION_TYPES.DELETE_WALLETTRANSACTION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_WALLETTRANSACTIONS):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_WALLETTRANSACTION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_WALLETTRANSACTION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_WALLETTRANSACTION):
    case SUCCESS(ACTION_TYPES.UPDATE_WALLETTRANSACTION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_WALLETTRANSACTION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/wallet-transactions';
const apiSearchUrl = 'api/_search/wallet-transactions';

// Actions

export const getSearchEntities: ICrudSearchAction<IWalletTransaction> = query => ({
  type: ACTION_TYPES.SEARCH_WALLETTRANSACTIONS,
  payload: axios.get<IWalletTransaction>(`${apiSearchUrl}?query=` + query)
});

export const getEntities: ICrudGetAllAction<IWalletTransaction> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_WALLETTRANSACTION_LIST,
  payload: axios.get<IWalletTransaction>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IWalletTransaction> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_WALLETTRANSACTION,
    payload: axios.get<IWalletTransaction>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IWalletTransaction> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_WALLETTRANSACTION,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IWalletTransaction> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_WALLETTRANSACTION,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IWalletTransaction> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_WALLETTRANSACTION,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
