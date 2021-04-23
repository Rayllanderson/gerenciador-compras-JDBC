import {FiAlertCircle, FiCheckCircle, FiInfo, FiXCircle} from "react-icons/fi";
import {useContext, useEffect} from 'react';
import {ToastContext, ToastMessage} from "../../context/ToastContext";

interface Props {
  type?: string;
  message: ToastMessage;
}
export default function Toast({ message }: Props) {
  const { removeToast } = useContext(ToastContext);
  let background = '#ebf8ff';
  let color = '#3172b7';
  const icons = {
    info: <FiInfo size={20}/>,
    success: < FiCheckCircle size={20}/>,
    error: <FiAlertCircle size={20}/>,
  }
  switch (message.type) {
    case 'success':
      background = '#e6fffa';
      color = 'var(--green)';
      break;
    case 'error':
      background = 'var(--red)';
      color = '#c53030';
      break;
  }

  useEffect(() => {
    const timer = setTimeout(() => {
      removeToast(message.id)
    }, 4000)
    return () => {
      clearTimeout(timer)
    };
  }, [removeToast, message.id])

  return (
    <div className="toast-element toast-right" style={{ background: background, color: color }}>
      {icons[message.type || 'info']}
      <div>
        <strong> {message.title} </strong>
        <p>{message.description}</p>
      </div>
      <button onClick={() => removeToast(message.id)}>
        <FiXCircle size={18} />
      </button>
    </div>
  )
}