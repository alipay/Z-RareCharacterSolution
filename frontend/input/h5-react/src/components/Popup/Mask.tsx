import { animated, useSpring } from '@react-spring/web';
import classnames from 'classnames';
import React, { useState } from 'react';
import { ShouldRender } from './should-render';
import useUnmountedRef from './use-unmount-ref';
import './mask.less';

interface IMaskProps {
  className: string;
  visible: boolean;
  forceRender?: boolean;
  destroyOnClose?: boolean;
  onMaskClick?: (e: React.MouseEvent<HTMLDivElement, MouseEvent>) => void;
}

const Mask: React.FC<IMaskProps> = (props) => {
  const [active, setActive] = useState(props.visible);

  const unmountedRef = useUnmountedRef();
  const { opacity } = useSpring({
    opacity: props.visible ? 1 : 0,
    config: {
      precision: 0.01,
      mass: 1,
      tension: 250,
      friction: 30,
      clamp: true,
    },
    onStart: () => {
      setActive(true);
    },
    onRest: () => {
      if (unmountedRef.current) return;
      setActive(props.visible);
    },
  });

  return (
    <ShouldRender
      active={props.visible}
      forceRender={props.forceRender}
      destroyOnClose={props.destroyOnClose}
    >
      <animated.div
        className={classnames(props.className, 'rare-words-input__mask')}
        style={{
          background: 'rgba(0, 0, 0, 0.45)',
          opacity,
          display: active ? undefined : 'none',
        }}
        onClick={(e) => {
          if (e.target === e.currentTarget) {
            props.onMaskClick?.(e);
          }
        }}
      />
    </ShouldRender>
  );
};

export default Mask;
