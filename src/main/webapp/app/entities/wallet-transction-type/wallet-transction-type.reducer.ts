import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IWalletTransctionType, defaultValue } from 'app/shared/model/wallet-transction-type.model';

export const ACTION_TYPES = {
  SEARCH_WALLETTRANSCTIONTYPES: 'walletTransctionType/SEARCH_WALLETTRANSCTIONTYPES',
  FETCH_WALLETTRANSCTIONTYPE_LIST: 'walletTransctionType/FETCH_WALLETTRANSCTIONTYPE_LIST',
  FETCH_WALLETTRANSCTIONTYPE: 'walletTransctionType/FETCH_WALLETTRANSCTIONTYPE',
  CREATE_WALLETTRANSCTIONTYPE: 'walletTransctionType/CREATE_WALLETTRANSCTIONTYPE',
  UPDATE_WALLETTRANSCTIONTYPE: 'walletTransctionType/UPDATE_WALLETTRANSCTIONTYPE',
  DELETE_WALLETTRANSCTIONTYPE: 'walletTransctionType/DELETE_WALLETTRANSCTIONTYPE',
  RESET: 'walletTransctionType/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IWalletTransctionType>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type WalletTransctionTypeState = Readonly<typeof initialState>;

// Reducer

export default (state: WalletTransctionTypeState = initialState, action): WalletTransctionTypeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_WALLETTRANSCTIONTYPES):
    case REQUEST(ACTION_TYPES.FETCH_WALLETTRANSCTIONTYPE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_WALLETTRANSCTIONTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_WALLETTRANSCTIONTYPE):
    case REQUEST(ACTION_TYPES.UPDATE_WALLETTRANSCTIONTYPE):
    case REQUEST(ACTION_TYPES.DELETE_WALLETTRANSCTIONTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_WALLETTRANSCTIONTYPES):
    case FAILURE(ACTION_TYPES.FETCH_WALLETTRANSCTIONTYPE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_WALLETTRANSCTIONTYPE):
    case FAILURE(ACTION_TYPES.CREATE_WALLETTRANSCTIONTYPE):
    case FAILURE(ACTION_TYPES.UPDATE_WALLETTRANSCTIONTYPE):
    case FAILURE(ACTION_TYPES.DELETE_WALLETTRANSCTIONTYPE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_WALLETTRANSCTIONTYPES):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_WALLETTRANSCTIONTYPE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_WALLETTRANSCTIONTYPE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_WALLETTRANSCTIONTYPE):
    case SUCCESS(ACTION_TYPES.UPDATE_WALLETTRANSCTIONTYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_WALLETTRANSCTIONTYPE):
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

const apiUrl = 'api/wallet-transction-types';
const apiSearchUrl = 'api/_search/wallet-transction-types';

// Actions

export const getSearchEntities: ICrudSearchAction<IWalletTransctionType> = query => ({
  type: ACTION_TYPES.SEARCH_WALLETTRANSCTIONTYPES,
  payload: axios.get<IWalletTransctionType>(`${apiSearchUrl}?query=` + query)
});

export const getEntities: ICrudGetAllAction<IWalletTransctionType> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_WALLETTRANSCTIONTYPE_LIST,
  payload: axios.get<IWalletTransctionType>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IWalletTransctionType> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_WALLETTRANSCTIONTYPE,
    payload: axios.get<IWalletTransctionType>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IWalletTransctionType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_WALLETTRANSCTIONTYPE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IWalletTransctionType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_WALLETTRANSCTIONTYPE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IWalletTransctionType> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_WALLETTRANSCTIONTYPE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
