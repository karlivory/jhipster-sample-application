import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICurrentReading, defaultValue } from 'app/shared/model/current-reading.model';

export const ACTION_TYPES = {
  FETCH_CURRENTREADING_LIST: 'currentReading/FETCH_CURRENTREADING_LIST',
  FETCH_CURRENTREADING: 'currentReading/FETCH_CURRENTREADING',
  CREATE_CURRENTREADING: 'currentReading/CREATE_CURRENTREADING',
  UPDATE_CURRENTREADING: 'currentReading/UPDATE_CURRENTREADING',
  DELETE_CURRENTREADING: 'currentReading/DELETE_CURRENTREADING',
  RESET: 'currentReading/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICurrentReading>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type CurrentReadingState = Readonly<typeof initialState>;

// Reducer

export default (state: CurrentReadingState = initialState, action): CurrentReadingState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CURRENTREADING_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CURRENTREADING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CURRENTREADING):
    case REQUEST(ACTION_TYPES.UPDATE_CURRENTREADING):
    case REQUEST(ACTION_TYPES.DELETE_CURRENTREADING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CURRENTREADING_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CURRENTREADING):
    case FAILURE(ACTION_TYPES.CREATE_CURRENTREADING):
    case FAILURE(ACTION_TYPES.UPDATE_CURRENTREADING):
    case FAILURE(ACTION_TYPES.DELETE_CURRENTREADING):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CURRENTREADING_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CURRENTREADING):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CURRENTREADING):
    case SUCCESS(ACTION_TYPES.UPDATE_CURRENTREADING):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CURRENTREADING):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/current-readings';

// Actions

export const getEntities: ICrudGetAllAction<ICurrentReading> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CURRENTREADING_LIST,
  payload: axios.get<ICurrentReading>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ICurrentReading> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CURRENTREADING,
    payload: axios.get<ICurrentReading>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICurrentReading> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CURRENTREADING,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICurrentReading> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CURRENTREADING,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICurrentReading> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CURRENTREADING,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
