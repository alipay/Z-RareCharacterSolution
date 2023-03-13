import React from 'react';
import './index.less';

interface IProps {
  value: string[];
}

const OperationArea: React.FC<IProps> = (props) => {
  return <div className="rare-words-input__operation-area">{props.value}</div>;
};

export { OperationArea };
