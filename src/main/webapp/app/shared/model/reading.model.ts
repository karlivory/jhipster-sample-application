import { Moment } from 'moment';
import { IDevice } from 'app/shared/model/device.model';
import { ReadingType } from 'app/shared/model/enumerations/reading-type.model';

export interface IReading {
  id?: number;
  type?: ReadingType;
  value?: number;
  timestamp?: string;
  device?: IDevice;
}

export const defaultValue: Readonly<IReading> = {};
