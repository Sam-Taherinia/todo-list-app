# WARP.md

This file provides guidance to WARP (warp.dev) when working with code in this repository.

## Project Overview

This is the frontend UI for a ToDo list web application built with React, TypeScript, and Vite. The application provides a modern, responsive interface for managing task lists and individual tasks, with a clean architecture that communicates with a Spring Boot backend via REST APIs.

**Technology Stack:**
- React 18 with TypeScript
- Vite for build tooling and development server
- NextUI for component library
- Tailwind CSS for styling
- Framer Motion for animations
- React Router DOM for navigation
- Axios for HTTP requests
- Lucide React for icons

**UI Framework:**
- **NextUI**: Modern React UI library providing pre-built components
- **Tailwind CSS**: Utility-first CSS framework for styling
- **Framer Motion**: Animation library for smooth transitions

## Architecture

The application follows a well-structured React architecture with clear separation of concerns:

### Core Components
- **App.tsx**: Main application component with React Router configuration
- **AppProvider.tsx**: Global state management using React Context and useReducer
- **Components**: Screen-based components for different views

### Domain Models (TypeScript Interfaces/Enums)
- **Task**: Core task entity with title, description, due date, priority, and status
- **TaskList**: Container for tasks with metadata (title, description, progress tracking)
- **TaskPriority**: Enum for HIGH, MEDIUM, LOW priorities
- **TaskStatus**: Enum for OPEN, CLOSED states

### State Management Architecture
- **React Context + useReducer**: Global state management pattern
- **Action-based updates**: Dispatched actions for all state mutations
- **API integration**: Axios-based HTTP client with automatic state synchronization
- **Local state caching**: Tasks organized by taskListId for efficient access

### Component Structure
- **TaskListsScreen**: Main dashboard showing all task lists
- **TasksScreen**: Individual task list view showing tasks
- **CreateUpdateTaskListScreen**: Form for creating/editing task lists
- **CreateUpdateTaskScreen**: Form for creating/editing individual tasks

### API Integration
- **REST API communication**: Full CRUD operations for both task lists and tasks
- **Proxy configuration**: Vite dev server proxies `/api` requests to `http://localhost:8080`
- **Automatic state sync**: API calls automatically update global state via reducer actions

## Development Commands

### Setup & Installation
```powershell
# Install dependencies
npm install

# Clean installation (if needed)
npm run clean
npm install
```

### Development
```powershell
# Start development server (with HMR)
npm run dev

# Development server will start on http://localhost:5173
# API requests to /api/* will be proxied to http://localhost:8080
```

### Build & Preview
```powershell
# Build for production
npm run build

# Preview production build locally
npm run preview

# Type check without building
npx tsc --noEmit
```

### Code Quality
```powershell
# Run ESLint
npm run lint

# Fix ESLint issues automatically
npm run lint -- --fix

# Format with Prettier (if configured)
npm run format
```

### Testing
```powershell
# Run tests (when test framework is added)
npm test

# Run tests in watch mode
npm test -- --watch
```

## Configuration Files

- **vite.config.ts**: Vite configuration with React plugin and API proxy
- **tsconfig.json**: TypeScript compiler configuration
- **tailwind.config.js**: Tailwind CSS customization
- **eslint.config.js**: ESLint rules and configuration
- **package.json**: Dependencies and npm scripts

## Development Workflow

### Backend Dependency
The frontend requires the Spring Boot backend to be running on `http://localhost:8080` for full functionality. The Vite dev server is configured to proxy API requests automatically.

### State Management Pattern
1. **Actions**: All state changes go through dispatched actions
2. **API Calls**: Automatically trigger state updates via reducer
3. **Context**: Global state accessible via `useAppContext()` hook
4. **Local Caching**: Tasks cached by taskListId for performance

### Routing Structure
- `/` - Task lists dashboard
- `/new-task-list` - Create new task list
- `/edit-task-list/:listId` - Edit existing task list  
- `/task-lists/:listId` - View tasks in a specific list
- `/task-lists/:listId/new-task` - Create new task in list
- `/task-lists/:listId/edit-task/:taskId` - Edit specific task

## API Endpoints Expected

The frontend expects these REST API endpoints from the backend:

### Task Lists
- `GET /api/task-lists` - Fetch all task lists
- `GET /api/task-lists/:id` - Get specific task list
- `POST /api/task-lists` - Create new task list
- `PUT /api/task-lists/:id` - Update task list
- `DELETE /api/task-lists/:id` - Delete task list

### Tasks
- `GET /api/task-lists/:listId/tasks` - Fetch tasks in a list
- `GET /api/task-lists/:listId/tasks/:taskId` - Get specific task
- `POST /api/task-lists/:listId/tasks` - Create new task
- `PUT /api/task-lists/:listId/tasks/:taskId` - Update task
- `DELETE /api/task-lists/:listId/tasks/:taskId` - Delete task
