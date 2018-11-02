import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPartnerLog, defaultValue } from 'app/shared/model/partner-log.model';

export const ACTION_TYPES = {
  SEARCH_PARTNERLOGS: 'partnerLog/SEARCH_PARTNERLOGS',
  FETCH_PARTNERLOG_LIST: 'partnerLog/FETCH_PARTNERLOG_LIST',
  FETCH_PARTNERLOG: 'partnerLog/FETCH_PARTNERLOG',
  CREATE_PARTNERLOG: 'partnerLog/CREATE_PARTNERLOG',
  UPDATE_PARTNERLOG: 'partnerLog/UPDATE_PARTNERLOG',
  DELETE_PARTNERLOG: 'partnerLog/DELETE_PARTNERLOG',
  RESET: 'partnerLog/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPartnerLog>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type PartnerLogState = Readonly<typeof initialState>;

// Reducer

export default (state: PartnerLogState = initialState, action): PartnerLogState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_PARTNERLOGS):
    case REQUEST(ACTION_TYPES.FETCH_PARTNERLOG_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PARTNERLOG):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PARTNERLOG):
    case REQUEST(ACTION_TYPES.UPDATE_PARTNERLOG):
    case REQUEST(ACTION_TYPES.DELETE_PARTNERLOG):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_PARTNERLOGS):
    case FAILURE(ACTION_TYPES.FETCH_PARTNERLOG_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PARTNERLOG):
    case FAILURE(ACTION_TYPES.CREATE_PARTNERLOG):
    case FAILURE(ACTION_TYPES.UPDATE_PARTNERLOG):
    case FAILURE(ACTION_TYPES.DELETE_PARTNERLOG):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_PARTNERLOGS):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PARTNERLOG_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PARTNERLOG):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PARTNERLOG):
    case SUCCESS(ACTION_TYPES.UPDATE_PARTNERLOG):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PARTNERLOG):
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

const apiUrl = 'api/partner-logs';
const apiSearchUrl = 'api/_search/partner-logs';

// Actions

export const getSearchEntities: ICrudSearchAction<IPartnerLog> = query => ({
  type: ACTION_TYPES.SEARCH_PARTNERLOGS,
  payload: axios.get<IPartnerLog>(`${apiSearchUrl}?query=` + query)
});

export const getEntities: ICrudGetAllAction<IPartnerLog> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PARTNERLOG_LIST,
  payload: axios.get<IPartnerLog>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IPartnerLog> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PARTNERLOG,
    payload: axios.get<IPartnerLog>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPartnerLog> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PARTNERLOG,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPartnerLog> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PARTNERLOG,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPartnerLog> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PARTNERLOG,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
