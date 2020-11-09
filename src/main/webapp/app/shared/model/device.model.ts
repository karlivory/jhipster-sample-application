export interface IDevice {
  id?: number;
  name?: string;
  location?: string;
}

export const defaultValue: Readonly<IDevice> = {};
