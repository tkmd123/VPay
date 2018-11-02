import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IWalletRule, defaultValue } from 'app/shared/model/wallet-rule.model';

export const ACTION_TYPES = {
  SEARCH_WALLETRULES: 'walletRule/SEARCH_WALLETRULES',
  FETCH_WALLETRULE_LIST: 'walletRule/FETCH_WALLETRULE_LIST',
  FETCH_WALLETRULE: 'walletRule/FETCH_WALLETRULE',
  CREATE_WALLETRULE: 'walletRule/CREATE_WALLETRULE',
  UPDATE_WALLETRULE: 'walletRule/UPDATE_WALLETRULE',
  DELETE_WALLETRULE: 'walletRule/DELETE_WALLETRULE',
  RESET: 'walletRule/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IWalletRule>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type WalletRuleState = Readonly<typeof initialState>;

// Reducer

export default (state: WalletRuleState = initialState, action): WalletRuleState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_WALLETRULES):
    case REQUEST(ACTION_TYPES.FETCH_WALLETRULE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_WALLETRULE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_WALLETRULE):
    case REQUEST(ACTION_TYPES.UPDATE_WALLETRULE):
    case REQUEST(ACTION_TYPES.DELETE_WALLETRULE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_WALLETRULES):
    case FAILURE(ACTION_TYPES.FETCH_WALLETRULE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_WALLETRULE):
    case FAILURE(ACTION_TYPES.CREATE_WALLETRULE):
    case FAILURE(ACTION_TYPES.UPDATE_WALLETRULE):
    case FAILURE(ACTION_TYPES.DELETE_WALLETRULE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_WALLETRULES):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_WALLETRULE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_WALLETRULE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_WALLETRULE):
    case SUCCESS(ACTION_TYPES.UPDATE_WALLETRULE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_WALLETRULE):
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

const apiUrl = 'api/wallet-rules';
const apiSearchUrl = 'api/_search/wallet-rules';

// Actions

export const getSearchEntities: ICrudSearchAction<IWalletRule> = query => ({
  type: ACTION_TYPES.SEARCH_WALLETRULES,
  payload: axios.get<IWalletRule>(`${apiSearchUrl}?query=` + query)
});

export const getEntities: ICrudGetAllAction<IWalletRule> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_WALLETRULE_LIST,
  payload: axios.get<IWalletRule>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IWalletRule> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_WALLETRULE,
    payload: axios.get<IWalletRule>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IWalletRule> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_WALLETRULE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IWalletRule> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_WALLETRULE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IWalletRule> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_WALLETRULE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
