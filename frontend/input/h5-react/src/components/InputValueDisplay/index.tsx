import cls from 'classnames';
import React, { useEffect, useState } from 'react';
import './index.less';

interface IProps {
  value: string[];
}

const InputValueDisplay: React.FC<IProps> = (props) => {
  const [visible, setVisible] = useState(props.value?.length > 0);

  useEffect(() => {
    setVisible(props.value?.length > 0);
  }, [props.value]);

  return (
    <div className={cls('rare-words-input__input-value', { 'hide': !visible })}>
      {props.value}
    </div>
  );
};

export { InputValueDisplay };
