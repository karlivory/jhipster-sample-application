import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IControllerDevice, defaultValue } from 'app/shared/model/controller-device.model';

export const ACTION_TYPES = {
  FETCH_CONTROLLERDEVICE_LIST: 'controllerDevice/FETCH_CONTROLLERDEVICE_LIST',
  FETCH_CONTROLLERDEVICE: 'controllerDevice/FETCH_CONTROLLERDEVICE',
  CREATE_CONTROLLERDEVICE: 'controllerDevice/CREATE_CONTROLLERDEVICE',
  UPDATE_CONTROLLERDEVICE: 'controllerDevice/UPDATE_CONTROLLERDEVICE',
  DELETE_CONTROLLERDEVICE: 'controllerDevice/DELETE_CONTROLLERDEVICE',
  RESET: 'controllerDevice/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IControllerDevice>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ControllerDeviceState = Readonly<typeof initialState>;

// Reducer

export default (state: ControllerDeviceState = initialState, action): ControllerDeviceState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CONTROLLERDEVICE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CONTROLLERDEVICE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CONTROLLERDEVICE):
    case REQUEST(ACTION_TYPES.UPDATE_CONTROLLERDEVICE):
    case REQUEST(ACTION_TYPES.DELETE_CONTROLLERDEVICE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CONTROLLERDEVICE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CONTROLLERDEVICE):
    case FAILURE(ACTION_TYPES.CREATE_CONTROLLERDEVICE):
    case FAILURE(ACTION_TYPES.UPDATE_CONTROLLERDEVICE):
    case FAILURE(ACTION_TYPES.DELETE_CONTROLLERDEVICE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONTROLLERDEVICE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONTROLLERDEVICE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CONTROLLERDEVICE):
    case SUCCESS(ACTION_TYPES.UPDATE_CONTROLLERDEVICE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CONTROLLERDEVICE):
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

const apiUrl = 'api/controller-devices';

// Actions

export const getEntities: ICrudGetAllAction<IControllerDevice> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CONTROLLERDEVICE_LIST,
  payload: axios.get<IControllerDevice>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IControllerDevice> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CONTROLLERDEVICE,
    payload: axios.get<IControllerDevice>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IControllerDevice> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CONTROLLERDEVICE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IControllerDevice> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CONTROLLERDEVICE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IControllerDevice> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CONTROLLERDEVICE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
