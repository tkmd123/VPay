import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IWalletAllotment, defaultValue } from 'app/shared/model/wallet-allotment.model';

export const ACTION_TYPES = {
  SEARCH_WALLETALLOTMENTS: 'walletAllotment/SEARCH_WALLETALLOTMENTS',
  FETCH_WALLETALLOTMENT_LIST: 'walletAllotment/FETCH_WALLETALLOTMENT_LIST',
  FETCH_WALLETALLOTMENT: 'walletAllotment/FETCH_WALLETALLOTMENT',
  CREATE_WALLETALLOTMENT: 'walletAllotment/CREATE_WALLETALLOTMENT',
  UPDATE_WALLETALLOTMENT: 'walletAllotment/UPDATE_WALLETALLOTMENT',
  DELETE_WALLETALLOTMENT: 'walletAllotment/DELETE_WALLETALLOTMENT',
  RESET: 'walletAllotment/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IWalletAllotment>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type WalletAllotmentState = Readonly<typeof initialState>;

// Reducer

export default (state: WalletAllotmentState = initialState, action): WalletAllotmentState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_WALLETALLOTMENTS):
    case REQUEST(ACTION_TYPES.FETCH_WALLETALLOTMENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_WALLETALLOTMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_WALLETALLOTMENT):
    case REQUEST(ACTION_TYPES.UPDATE_WALLETALLOTMENT):
    case REQUEST(ACTION_TYPES.DELETE_WALLETALLOTMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_WALLETALLOTMENTS):
    case FAILURE(ACTION_TYPES.FETCH_WALLETALLOTMENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_WALLETALLOTMENT):
    case FAILURE(ACTION_TYPES.CREATE_WALLETALLOTMENT):
    case FAILURE(ACTION_TYPES.UPDATE_WALLETALLOTMENT):
    case FAILURE(ACTION_TYPES.DELETE_WALLETALLOTMENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_WALLETALLOTMENTS):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_WALLETALLOTMENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_WALLETALLOTMENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_WALLETALLOTMENT):
    case SUCCESS(ACTION_TYPES.UPDATE_WALLETALLOTMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_WALLETALLOTMENT):
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

const apiUrl = 'api/wallet-allotments';
const apiSearchUrl = 'api/_search/wallet-allotments';

// Actions

export const getSearchEntities: ICrudSearchAction<IWalletAllotment> = query => ({
  type: ACTION_TYPES.SEARCH_WALLETALLOTMENTS,
  payload: axios.get<IWalletAllotment>(`${apiSearchUrl}?query=` + query)
});

export const getEntities: ICrudGetAllAction<IWalletAllotment> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_WALLETALLOTMENT_LIST,
  payload: axios.get<IWalletAllotment>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IWalletAllotment> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_WALLETALLOTMENT,
    payload: axios.get<IWalletAllotment>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IWalletAllotment> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_WALLETALLOTMENT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IWalletAllotment> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_WALLETALLOTMENT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IWalletAllotment> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_WALLETALLOTMENT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
