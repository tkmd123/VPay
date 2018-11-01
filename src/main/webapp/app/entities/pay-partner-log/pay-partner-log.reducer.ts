import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPayPartnerLog, defaultValue } from 'app/shared/model/pay-partner-log.model';

export const ACTION_TYPES = {
  SEARCH_PAYPARTNERLOGS: 'payPartnerLog/SEARCH_PAYPARTNERLOGS',
  FETCH_PAYPARTNERLOG_LIST: 'payPartnerLog/FETCH_PAYPARTNERLOG_LIST',
  FETCH_PAYPARTNERLOG: 'payPartnerLog/FETCH_PAYPARTNERLOG',
  CREATE_PAYPARTNERLOG: 'payPartnerLog/CREATE_PAYPARTNERLOG',
  UPDATE_PAYPARTNERLOG: 'payPartnerLog/UPDATE_PAYPARTNERLOG',
  DELETE_PAYPARTNERLOG: 'payPartnerLog/DELETE_PAYPARTNERLOG',
  RESET: 'payPartnerLog/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPayPartnerLog>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type PayPartnerLogState = Readonly<typeof initialState>;

// Reducer

export default (state: PayPartnerLogState = initialState, action): PayPartnerLogState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_PAYPARTNERLOGS):
    case REQUEST(ACTION_TYPES.FETCH_PAYPARTNERLOG_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PAYPARTNERLOG):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PAYPARTNERLOG):
    case REQUEST(ACTION_TYPES.UPDATE_PAYPARTNERLOG):
    case REQUEST(ACTION_TYPES.DELETE_PAYPARTNERLOG):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_PAYPARTNERLOGS):
    case FAILURE(ACTION_TYPES.FETCH_PAYPARTNERLOG_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PAYPARTNERLOG):
    case FAILURE(ACTION_TYPES.CREATE_PAYPARTNERLOG):
    case FAILURE(ACTION_TYPES.UPDATE_PAYPARTNERLOG):
    case FAILURE(ACTION_TYPES.DELETE_PAYPARTNERLOG):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_PAYPARTNERLOGS):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PAYPARTNERLOG_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PAYPARTNERLOG):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PAYPARTNERLOG):
    case SUCCESS(ACTION_TYPES.UPDATE_PAYPARTNERLOG):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PAYPARTNERLOG):
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

const apiUrl = 'api/pay-partner-logs';
const apiSearchUrl = 'api/_search/pay-partner-logs';

// Actions

export const getSearchEntities: ICrudSearchAction<IPayPartnerLog> = query => ({
  type: ACTION_TYPES.SEARCH_PAYPARTNERLOGS,
  payload: axios.get<IPayPartnerLog>(`${apiSearchUrl}?query=` + query)
});

export const getEntities: ICrudGetAllAction<IPayPartnerLog> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PAYPARTNERLOG_LIST,
  payload: axios.get<IPayPartnerLog>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IPayPartnerLog> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PAYPARTNERLOG,
    payload: axios.get<IPayPartnerLog>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPayPartnerLog> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PAYPARTNERLOG,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPayPartnerLog> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PAYPARTNERLOG,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPayPartnerLog> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PAYPARTNERLOG,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
