import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import device, {
  DeviceState
} from 'app/entities/device/device.reducer';
// prettier-ignore
import controllerDevice, {
  ControllerDeviceState
} from 'app/entities/controller-device/controller-device.reducer';
// prettier-ignore
import actuatorDevice, {
  ActuatorDeviceState
} from 'app/entities/actuator-device/actuator-device.reducer';
// prettier-ignore
import sensorDevice, {
  SensorDeviceState
} from 'app/entities/sensor-device/sensor-device.reducer';
// prettier-ignore
import reading, {
  ReadingState
} from 'app/entities/reading/reading.reducer';
// prettier-ignore
import currentReading, {
  CurrentReadingState
} from 'app/entities/current-reading/current-reading.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly device: DeviceState;
  readonly controllerDevice: ControllerDeviceState;
  readonly actuatorDevice: ActuatorDeviceState;
  readonly sensorDevice: SensorDeviceState;
  readonly reading: ReadingState;
  readonly currentReading: CurrentReadingState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  device,
  controllerDevice,
  actuatorDevice,
  sensorDevice,
  reading,
  currentReading,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
