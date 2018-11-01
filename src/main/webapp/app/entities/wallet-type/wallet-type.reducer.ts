import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IWalletType, defaultValue } from 'app/shared/model/wallet-type.model';

export const ACTION_TYPES = {
  SEARCH_WALLETTYPES: 'walletType/SEARCH_WALLETTYPES',
  FETCH_WALLETTYPE_LIST: 'walletType/FETCH_WALLETTYPE_LIST',
  FETCH_WALLETTYPE: 'walletType/FETCH_WALLETTYPE',
  CREATE_WALLETTYPE: 'walletType/CREATE_WALLETTYPE',
  UPDATE_WALLETTYPE: 'walletType/UPDATE_WALLETTYPE',
  DELETE_WALLETTYPE: 'walletType/DELETE_WALLETTYPE',
  RESET: 'walletType/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IWalletType>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type WalletTypeState = Readonly<typeof initialState>;

// Reducer

export default (state: WalletTypeState = initialState, action): WalletTypeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_WALLETTYPES):
    case REQUEST(ACTION_TYPES.FETCH_WALLETTYPE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_WALLETTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_WALLETTYPE):
    case REQUEST(ACTION_TYPES.UPDATE_WALLETTYPE):
    case REQUEST(ACTION_TYPES.DELETE_WALLETTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_WALLETTYPES):
    case FAILURE(ACTION_TYPES.FETCH_WALLETTYPE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_WALLETTYPE):
    case FAILURE(ACTION_TYPES.CREATE_WALLETTYPE):
    case FAILURE(ACTION_TYPES.UPDATE_WALLETTYPE):
    case FAILURE(ACTION_TYPES.DELETE_WALLETTYPE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_WALLETTYPES):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_WALLETTYPE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_WALLETTYPE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_WALLETTYPE):
    case SUCCESS(ACTION_TYPES.UPDATE_WALLETTYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_WALLETTYPE):
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

const apiUrl = 'api/wallet-types';
const apiSearchUrl = 'api/_search/wallet-types';

// Actions

export const getSearchEntities: ICrudSearchAction<IWalletType> = query => ({
  type: ACTION_TYPES.SEARCH_WALLETTYPES,
  payload: axios.get<IWalletType>(`${apiSearchUrl}?query=` + query)
});

export const getEntities: ICrudGetAllAction<IWalletType> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_WALLETTYPE_LIST,
  payload: axios.get<IWalletType>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IWalletType> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_WALLETTYPE,
    payload: axios.get<IWalletType>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IWalletType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_WALLETTYPE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IWalletType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_WALLETTYPE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IWalletType> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_WALLETTYPE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
