import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IActuatorDevice, defaultValue } from 'app/shared/model/actuator-device.model';

export const ACTION_TYPES = {
  FETCH_ACTUATORDEVICE_LIST: 'actuatorDevice/FETCH_ACTUATORDEVICE_LIST',
  FETCH_ACTUATORDEVICE: 'actuatorDevice/FETCH_ACTUATORDEVICE',
  CREATE_ACTUATORDEVICE: 'actuatorDevice/CREATE_ACTUATORDEVICE',
  UPDATE_ACTUATORDEVICE: 'actuatorDevice/UPDATE_ACTUATORDEVICE',
  DELETE_ACTUATORDEVICE: 'actuatorDevice/DELETE_ACTUATORDEVICE',
  RESET: 'actuatorDevice/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IActuatorDevice>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ActuatorDeviceState = Readonly<typeof initialState>;

// Reducer

export default (state: ActuatorDeviceState = initialState, action): ActuatorDeviceState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ACTUATORDEVICE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ACTUATORDEVICE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ACTUATORDEVICE):
    case REQUEST(ACTION_TYPES.UPDATE_ACTUATORDEVICE):
    case REQUEST(ACTION_TYPES.DELETE_ACTUATORDEVICE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ACTUATORDEVICE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ACTUATORDEVICE):
    case FAILURE(ACTION_TYPES.CREATE_ACTUATORDEVICE):
    case FAILURE(ACTION_TYPES.UPDATE_ACTUATORDEVICE):
    case FAILURE(ACTION_TYPES.DELETE_ACTUATORDEVICE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ACTUATORDEVICE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ACTUATORDEVICE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ACTUATORDEVICE):
    case SUCCESS(ACTION_TYPES.UPDATE_ACTUATORDEVICE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ACTUATORDEVICE):
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

const apiUrl = 'api/actuator-devices';

// Actions

export const getEntities: ICrudGetAllAction<IActuatorDevice> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ACTUATORDEVICE_LIST,
  payload: axios.get<IActuatorDevice>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IActuatorDevice> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ACTUATORDEVICE,
    payload: axios.get<IActuatorDevice>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IActuatorDevice> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ACTUATORDEVICE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IActuatorDevice> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ACTUATORDEVICE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IActuatorDevice> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ACTUATORDEVICE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
