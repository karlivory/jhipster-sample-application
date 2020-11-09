import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISensorDevice, defaultValue } from 'app/shared/model/sensor-device.model';

export const ACTION_TYPES = {
  FETCH_SENSORDEVICE_LIST: 'sensorDevice/FETCH_SENSORDEVICE_LIST',
  FETCH_SENSORDEVICE: 'sensorDevice/FETCH_SENSORDEVICE',
  CREATE_SENSORDEVICE: 'sensorDevice/CREATE_SENSORDEVICE',
  UPDATE_SENSORDEVICE: 'sensorDevice/UPDATE_SENSORDEVICE',
  DELETE_SENSORDEVICE: 'sensorDevice/DELETE_SENSORDEVICE',
  RESET: 'sensorDevice/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISensorDevice>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type SensorDeviceState = Readonly<typeof initialState>;

// Reducer

export default (state: SensorDeviceState = initialState, action): SensorDeviceState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SENSORDEVICE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SENSORDEVICE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SENSORDEVICE):
    case REQUEST(ACTION_TYPES.UPDATE_SENSORDEVICE):
    case REQUEST(ACTION_TYPES.DELETE_SENSORDEVICE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SENSORDEVICE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SENSORDEVICE):
    case FAILURE(ACTION_TYPES.CREATE_SENSORDEVICE):
    case FAILURE(ACTION_TYPES.UPDATE_SENSORDEVICE):
    case FAILURE(ACTION_TYPES.DELETE_SENSORDEVICE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SENSORDEVICE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SENSORDEVICE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SENSORDEVICE):
    case SUCCESS(ACTION_TYPES.UPDATE_SENSORDEVICE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SENSORDEVICE):
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

const apiUrl = 'api/sensor-devices';

// Actions

export const getEntities: ICrudGetAllAction<ISensorDevice> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SENSORDEVICE_LIST,
  payload: axios.get<ISensorDevice>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ISensorDevice> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SENSORDEVICE,
    payload: axios.get<ISensorDevice>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ISensorDevice> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SENSORDEVICE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISensorDevice> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SENSORDEVICE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISensorDevice> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SENSORDEVICE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
