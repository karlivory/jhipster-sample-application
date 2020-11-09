import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IReading, defaultValue } from 'app/shared/model/reading.model';

export const ACTION_TYPES = {
  FETCH_READING_LIST: 'reading/FETCH_READING_LIST',
  FETCH_READING: 'reading/FETCH_READING',
  CREATE_READING: 'reading/CREATE_READING',
  UPDATE_READING: 'reading/UPDATE_READING',
  DELETE_READING: 'reading/DELETE_READING',
  RESET: 'reading/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IReading>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ReadingState = Readonly<typeof initialState>;

// Reducer

export default (state: ReadingState = initialState, action): ReadingState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_READING_LIST):
    case REQUEST(ACTION_TYPES.FETCH_READING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_READING):
    case REQUEST(ACTION_TYPES.UPDATE_READING):
    case REQUEST(ACTION_TYPES.DELETE_READING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_READING_LIST):
    case FAILURE(ACTION_TYPES.FETCH_READING):
    case FAILURE(ACTION_TYPES.CREATE_READING):
    case FAILURE(ACTION_TYPES.UPDATE_READING):
    case FAILURE(ACTION_TYPES.DELETE_READING):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_READING_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_READING):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_READING):
    case SUCCESS(ACTION_TYPES.UPDATE_READING):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_READING):
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

const apiUrl = 'api/readings';

// Actions

export const getEntities: ICrudGetAllAction<IReading> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_READING_LIST,
  payload: axios.get<IReading>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IReading> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_READING,
    payload: axios.get<IReading>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IReading> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_READING,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IReading> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_READING,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IReading> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_READING,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
